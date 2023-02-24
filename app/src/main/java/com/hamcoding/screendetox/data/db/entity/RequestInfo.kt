package com.hamcoding.screendetox.data.db.entity

import com.hamcoding.screendetox.util.DateFormatText

data class RequestInfo(
    val id: String = "",
    val senderEmail: String = "",
    val senderUid: String = "",
    val receiverEmail: String = "",
    val requestedDate: String = DateFormatText.getCurrentDate(),
    var acceptedDate: String? = null,
    var requestStatus: RequestStatus = RequestStatus.PENDING,
)

enum class RequestStatus{
    PENDING,
    FRIEND,
}
