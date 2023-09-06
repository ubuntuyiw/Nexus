package com.ubuntuyouiwe.nexus.presentation.state

data class WorkManagerState(
    val isEnqueued: Boolean = false,
    val isRunning: Boolean = false,
    val isSucceeded: Boolean = false,
    val isFailed: Boolean = false,
    val isBlocked: Boolean = false,
    val isCancelled: Boolean = false
)