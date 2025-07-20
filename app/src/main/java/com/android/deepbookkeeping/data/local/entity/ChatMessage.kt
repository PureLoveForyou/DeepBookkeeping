package com.android.deepbookkeeping.data.local.entity

data class ChatMessage(
    var id: Long,
    var isUser: Boolean = false,
    var done: Boolean,
    var message: String = "",
    var partialMsg: String = ""
)