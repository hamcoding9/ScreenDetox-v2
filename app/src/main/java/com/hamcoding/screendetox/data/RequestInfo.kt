package com.hamcoding.screendetox.data

import com.hamcoding.screendetox.util.DateFormatText
import java.text.DateFormat

data class RequestInfo(
    val id: String = "",
    val senderEmail: String = "",
    val receiverEmail: String = "",
    val requestedDate: String = DateFormatText.getCurrentDate(),
    var acceptedDate: String? = null,
    var requestStatus: RequestStatus = RequestStatus.PENDING,
)

enum class RequestStatus{
    PENDING,
    FRIEND,
}
