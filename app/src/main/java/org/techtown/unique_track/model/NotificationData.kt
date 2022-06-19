package org.techtown.unique_track.model

data class NotificationData (
    var productName : String ?= null,
    var productUID : String ?= null,
    var senderUID : String ?= null,
    var recieverUID : String ?= null,
    var transfercode : String ?= null
        )

