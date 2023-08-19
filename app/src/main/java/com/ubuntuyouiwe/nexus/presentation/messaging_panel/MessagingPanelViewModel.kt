package com.ubuntuyouiwe.nexus.presentation.messaging_panel

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.nl.languageid.LanguageIdentifier
import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.messages.Message
import com.ubuntuyouiwe.nexus.domain.model.messages.MessageItem
import com.ubuntuyouiwe.nexus.domain.use_case.GetRoleUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.firestore.GetChatRoomMessageUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.firestore.GetChatRoomUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.firestore.SendFirstMessageUseCase
import com.ubuntuyouiwe.nexus.presentation.state.ButtonState
import com.ubuntuyouiwe.nexus.presentation.state.SpeechState
import com.ubuntuyouiwe.nexus.presentation.state.TextFieldState
import com.ubuntuyouiwe.nexus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
) : ViewModel() {

    private val _roleState = mutableStateOf(RoleState())
    val rolesState: State<RoleState> = _roleState
    private val _sendMessageButtonState = mutableStateOf(ButtonState())
    val sendMessageButtonState: State<ButtonState> = _sendMessageButtonState

    private val _messageTextFieldState = mutableStateOf(TextFieldState())
    val messageTextFieldState: State<TextFieldState> = _messageTextFieldState

    private val _chatRoomState = mutableStateOf(ChatRoomState())
    val chatRoomState: State<ChatRoomState> = _chatRoomState

    private val _getMessagesState = mutableStateOf(GetMessagesState())
    val getMessagesState: State<GetMessagesState> = _getMessagesState

    private val _speechState = mutableStateOf(SpeechState())
    val speechState: State<SpeechState> = _speechState

    fun getTextToSpeech() = textToSpeech

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
                        data = it.data?: rolesState.value.data
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

    init {

        buttonState()
        savedStateHandle.get<String>("role")?.let { roleId ->
            getRole(roleId)
        }
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
                chatRoomState.value.copy(data = chatRoomState.value.data.copy(id = documentId ,isNew = true))
        }
        speechListener()
        getChatRooms(chatRoomState.value.data.id)
        getMessages(chatRoomState.value.data.id)

    }

    fun onEvent(event: MessagingPanelOnEvent) {
        when (event) {
            is MessagingPanelOnEvent.EnterMessage -> {
                _messageTextFieldState.value =
                    messageTextFieldState.value.copy(text = event.message)
                buttonState()
            }

            is MessagingPanelOnEvent.SendMessage -> {
                val totalMessageCount = chatRoomState.value.data.totalMessageCount
                val data = chatRoomState.value.data

                val messages = getMessagesState.value.data.messages.flatMap { it.messages.reversed() }.reversed()
                val updatedMessages = messages + listOf(MessageItem(event.content))

                Log.v("ssssssssssss", updatedMessages.toString())
                _chatRoomState.value = chatRoomState.value.copy(
                    data = data.copy(
                        roleId = rolesState.value.data.type,
                        role = rolesState.value.data,
                        totalMessageCount = totalMessageCount + 1
                    )
                )
                sendFirstMessage(chatRoomState.value.data, updatedMessages)

                _messageTextFieldState.value =
                    messageTextFieldState.value.copy(text = "")

                buttonState()

            }

            is MessagingPanelOnEvent.Speak -> {
                _speechState.value = event.content
                if (speechState.value.isSpeak)
                    speechStop()
                else speechSpeak()
            }

            is MessagingPanelOnEvent.NavigateUp -> {
                event.navController.navigateUp()
            }
        }

    }

    private fun speechStop() {
        _speechState.value = speechState.value.copy(isSpeak = false)
        textToSpeech.stop()
    }

    private fun speechSpeak() {
        languageIdentification.identifyLanguage(speechState.value.content)
            .addOnSuccessListener { languageCode ->
                textToSpeech.language = Locale.forLanguageTag(if (languageCode != "und") languageCode else "en")
                textToSpeech.speak(speechState.value.content, TextToSpeech.QUEUE_FLUSH, null, "")
            }
    }

    private fun speechListener() {
        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                _speechState.value = speechState.value.copy(isSpeak = true)
            }

            override fun onDone(utteranceId: String?) {
                _speechState.value = speechState.value.copy(isSpeak = false)
            }
            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {}
        })
    }

    private fun buttonState() {
        if (messageTextFieldState.value.text.isNotEmpty())
            _sendMessageButtonState.value =
                sendMessageButtonState.value.copy(enabled = true)
        else _sendMessageButtonState.value =
            sendMessageButtonState.value.copy(enabled = false)
    }

    private fun getChatRooms(id: String) {
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

    private fun sendFirstMessage(chatRoom: ChatRoom, messages: List<MessageItem>) {
        sendFirstMessageUseCase(chatRoom, messages).onEach {
            when (it) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    it.data
                }

                is Resource.Error -> {
                    it.message
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMessages(id: String) {
        getChatRoomMessageUseCase(id).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _getMessagesState.value = getMessagesState.value.copy(
                        isSuccess = true,
                        isError = false,
                        isLoading = false,
                        data = resource.data?: Message()
                    )

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