package org.techtown.unique_track.model

/* recyclerview 데이터 형식 - realtime database의 키와 변수의 이름이 같아야함 */
data class ItemData(
    val ProductName:String ?= null,
    val RegisterDate:String ?= null,
    val Image:String ?= null
)