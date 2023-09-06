package com.ubuntuyouiwe.nexus.data.util.dto_type.user

enum class UserDtoFields(val key: String) {
    UID("uid"),
    DISPLAY_NAME("displayName"),
    EMAIL("email"),
    PHOTO_URL("photoUrl"),
    IS_EMAIL_VERIFIED("isEmailVerified"),
    PHONE_NUMBER("phoneNumber"),
    TOTAL_COMPLETION_TOKENS("totalCompletionTokens"),
    TOTAL_PROMPT_TOKENS("totalPromptTokens"),
    TOTAL_TOKENS("totalTokens"),
    INFO("info"),
    RATE_LIMITING("rateLimiting")
}
