package com.ubuntuyouiwe.nexus.presentation.messaging_panel

import android.app.Application
import android.net.Uri
import android.os.Environment
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.mlkit.nl.languageid.LanguageIdentifier
import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.Settings
import com.ubuntuyouiwe.nexus.domain.model.messages.Message
import com.ubuntuyouiwe.nexus.domain.model.messages.MessageItem
import com.ubuntuyouiwe.nexus.domain.model.messages.Messages
import com.ubuntuyouiwe.nexus.domain.use_case.GetRoleUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.firestore.GetChatRoomMessageUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.firestore.GetChatRoomUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.firestore.SendFirstMessageUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.proto.settings.GetSettingsUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.proto.settings.UpdateSettingsUseCase
import com.ubuntuyouiwe.nexus.presentation.state.ButtonState
import com.ubuntuyouiwe.nexus.presentation.state.TextFieldState
import com.ubuntuyouiwe.nexus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MessagingPanelViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getChatRoomMessageUseCase: GetChatRoomMessageUseCase,
    private val sendFirstMessageUseCase: SendFirstMessageUseCase,
    private val getChatRoomUseCase: GetChatRoomUseCase,
    private val getRoleUseCase: GetRoleUseCase,
    private val textToSpeech: TextToSpeech,
    private val languageIdentification: LanguageIdentifier,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
    application: Application
) : AndroidViewModel(application) {

    private val _roleState = mutableStateOf(RoleState())
    val rolesState: State<RoleState> = _roleState

    private val _sendMessageButtonState = mutableStateOf(ButtonState())
    val sendMessageButtonState: State<ButtonState> = _sendMessageButtonState

    private val _sendMessageState = mutableStateOf(SendMessageState())
    val sendMessageState: State<SendMessageState> = _sendMessageState

    private val _messageTextFieldState = mutableStateOf(TextFieldState())
    val messageTextFieldState: State<TextFieldState> = _messageTextFieldState

    private val _chatRoomState = mutableStateOf(ChatRoomState())
    val chatRoomState: State<ChatRoomState> = _chatRoomState

    private val _getMessagesState = mutableStateOf(GetMessagesState())
    val getMessagesState: State<GetMessagesState> = _getMessagesState

    private val _settingsState = mutableStateOf(SettingsState())
    val settingsState: State<SettingsState> = _settingsState


    init {
        if (textToSpeech.defaultEngine != "com.google.android.tts") {
            Toast.makeText(
                application.applicationContext,
                "Lütfen Motoru değiştir.",
                Toast.LENGTH_LONG
            ).show()
        }
        buttonState()
        getRoles()
        getChatRoomId()
        speechListener()
        getSettings()
        getChatRoom(chatRoomState.value.data.id)
        getMessages(chatRoomState.value.data.id)

    }

    private fun getRoles() {
        savedStateHandle.get<String>("role")?.let { roleId ->
            getRole(roleId)
        }
    }

    private fun getChatRoomId() {
        savedStateHandle.get<String>("id")?.let { data ->
            _chatRoomState.value = chatRoomState.value.copy(
                data = chatRoomState.value.data.copy(
                    id = data,
                    isNew = false
                )
            )


        } ?: run {
            val documentId = UUID.randomUUID().toString()
            _chatRoomState.value =
                chatRoomState.value.copy(
                    data = chatRoomState.value.data.copy(
                        id = documentId,
                        isNew = true
                    )
                )
        }
    }

    fun onEvent(event: MessagingPanelOnEvent) {
        when (event) {
            is MessagingPanelOnEvent.EnterMessage -> {
                _messageTextFieldState.value =
                    messageTextFieldState.value.copy(text = event.message)
                buttonState()
            }

            is MessagingPanelOnEvent.SendMessage -> {
                _chatRoomState.value = chatRoomState.value.copy(
                    data = chatRoomState.value.data.copy(
                        roleId = rolesState.value.data.type,
                        role = rolesState.value.data,
                    )
                )
                sendFirstMessage(
                    chatRoomState.value.data,
                    getMessagesState.value.data,
                    MessageItem(content = event.content)
                )

                _messageTextFieldState.value =
                    messageTextFieldState.value.copy(text = "")


                buttonState()

            }

            is MessagingPanelOnEvent.Speak -> {

                _getMessagesState.value = getMessagesState.value.copy(
                    data = getMessagesState.value.data.copy(
                        messages = getMessagesState.value.data.messages.map {
                            if (it.id == event.content.id) event.content else it.copy(
                                isSpeak = false
                            )
                        }
                    )
                )
                if (event.content.isSpeak) speechSpeak()
                else speechStop()
            }

            is MessagingPanelOnEvent.NavigateUp -> {
                event.navController.navigateUp()
            }

            is MessagingPanelOnEvent.SetSpeechRate -> {
                if (settingsState.value.data.setSpeechRate == 1f)  setSpeechRate(1.5f)
                if (settingsState.value.data.setSpeechRate == 1.5f)  setSpeechRate(2f)
                if (settingsState.value.data.setSpeechRate == 2f)  setSpeechRate(1f)
            }
        }

    }

    private val photoFile = File(
        application.applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
        "photo.jpg"
    )
    val photoUri: Uri = FileProvider.getUriForFile(
        application.applicationContext,
        "${application.applicationContext.packageName}.fileprovider",
        photoFile
    )


    private fun getRole(id: String) {

        getRoleUseCase(id).onEach {
            when (it) {
                is Resource.Loading -> {
                    _roleState.value =
                        rolesState.value.copy(isLoading = true, isSuccess = false, isError = false)
                }

                is Resource.Success -> {
                    _roleState.value = rolesState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        data = it.data ?: rolesState.value.data
                    )
                }

                is Resource.Error -> {
                    _roleState.value = rolesState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = it.message
                    )
                }


            }
        }.launchIn(viewModelScope)

    }



    private fun getSettings() {
        getSettingsUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _settingsState.value = settingsState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                    Log.v("aaaaaaaaa","loading")
                }

                is Resource.Success -> {
                    _settingsState.value = settingsState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        data = resource.data ?: Settings()
                    )
                    Log.v("aaaaaaaaa","sss")

                    textToSpeech.setSpeechRate(resource.data?.setSpeechRate?: 1f)
                    textToSpeech.stop()
                    val data = getMessagesState.value.data.messages.firstOrNull() { it.isSpeak }
                    data?.let {
                        speechSpeak()
                    }
                }

                is Resource.Error -> {
                    _settingsState.value = settingsState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message
                    )
                    Log.v("aaaaaaaaa","eee")

                }
            }
        }.launchIn(viewModelScope)
    }

    private fun speechStop() {
/*
        _speechState.value = speechState.value.copy(isSpeak = false)
*/
        _getMessagesState.value = getMessagesState.value.copy(
            data = getMessagesState.value.data.copy(
                messages = getMessagesState.value.data.messages.map { messages ->
                    messages.copy(
                        isSpeak = false
                    )
                }
            )
        )
        textToSpeech.stop()
    }

    private fun speechSpeak() {

        val speakMessage = getMessagesState.value.data.messages.firstOrNull() { it.isSpeak }
        speakMessage?.let { messages ->

            languageIdentification.identifyLanguage(messages.messages[1].content)
                .addOnSuccessListener { languageCode ->
                    val data = getMessagesState.value.data.messages.map { if (it.isSpeak) it.copy(codeLanguage = languageCode ) else it }

                    _getMessagesState.value = getMessagesState.value.copy(
                        data = getMessagesState.value.data.copy(
                            messages = data
                        )
                    )

                    if (languageCode != "und") {
                        textToSpeech.language =
                            Locale.forLanguageTag(languageCode)
                        textToSpeech.speak(
                            messages.messages[1].content,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            ""
                        )
                    } else {
                        TODO()
                    }

                }
        }

    }

    private fun setSpeechRate(rate: Float) {
        updateSettingsUseCase(rate).onEach {

        }.launchIn(viewModelScope)

    }

    private fun speechListener() {
        textToSpeech.setPitch(0.9f)
        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
            }

            override fun onDone(utteranceId: String?) {
                _getMessagesState.value = getMessagesState.value.copy(
                    data = getMessagesState.value.data.copy(
                        messages = getMessagesState.value.data.messages.map { messages ->
                            messages.copy(
                                isSpeak = false
                            )
                        }
                    )
                )
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
            }
        })
    }

    private fun buttonState() {
        if (messageTextFieldState.value.text.isNotEmpty())
            _sendMessageButtonState.value =
                sendMessageButtonState.value.copy(enabled = true)
        else _sendMessageButtonState.value =
            sendMessageButtonState.value.copy(enabled = false)
    }

    private fun getChatRoom(id: String) {
        getChatRoomUseCase(id).onEach {
            when (it) {
                is Resource.Loading -> {
                    _chatRoomState.value = chatRoomState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false

                    )

                }

                is Resource.Success -> {
                    _chatRoomState.value = chatRoomState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        data = it.data ?: chatRoomState.value.data
                    )
                }

                is Resource.Error -> {
                    _chatRoomState.value = chatRoomState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = it.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun sendFirstMessage(chatRoom: ChatRoom, messages: Message, newMessage: MessageItem) {
        sendFirstMessageUseCase(chatRoom, messages, newMessage).onEach {
            when (it) {
                is Resource.Loading -> {
                    _sendMessageState.value = sendMessageState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                }

                is Resource.Success -> {
                    _sendMessageState.value = sendMessageState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false
                    )
                }

                is Resource.Error -> {
                    _sendMessageState.value = sendMessageState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = it.message
                    )

                }
            }
        }.launchIn(viewModelScope)
    }

    private fun mergeMessages(current: List<Messages>, incoming: List<Messages>): List<Messages> {
        return incoming.map { incomingMessage ->
            val existingMessage = current.find { it.id == incomingMessage.id }
            existingMessage?.let { existing ->
                val mergedMessageItems = incomingMessage.messages.map { incomingItem ->
                    existing.messages.find { it.content == incomingItem.content && it.role == incomingItem.role }
                        ?.let { existingItem ->
                            incomingItem.copy(
                                isExpanded = existingItem.isExpanded
                            )
                        } ?: incomingItem
                }

                incomingMessage.copy(
                    isSpeak = existing.isSpeak,
                    codeLanguage = existing.codeLanguage,
                    messages = mergedMessageItems
                )
            } ?: incomingMessage
        }
    }
    private fun getMessages(id: String) {

        getChatRoomMessageUseCase(id).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { incomingData ->
                        val currentData = _getMessagesState.value.data.messages
                        val mergedMessages = mergeMessages(currentData, incomingData.messages)

                        _getMessagesState.value = getMessagesState.value.copy(
                            isSuccess = true,
                            isError = false,
                            isLoading = false,
                            data = incomingData.copy(messages = mergedMessages)
                        )
                    }

                }

                is Resource.Error -> {
                    _getMessagesState.value = getMessagesState.value.copy(
                        isSuccess = false,
                        isError = true,
                        isLoading = false,
                        errorMessage = resource.message
                    )
                }

                is Resource.Loading -> {
                    _getMessagesState.value = getMessagesState.value.copy(
                        isSuccess = false,
                        isError = false,
                        isLoading = true,
                    )
                }

            }
        }.launchIn(viewModelScope)

    }

    override fun onCleared() {

        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
        super.onCleared()
    }


}