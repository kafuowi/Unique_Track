package org.techtown.unique_track.model

/* recyclerview 데이터 형식 - realtime database의 키와 변수의 이름이 같아야함 */
data class ItemData(
    val productName:String ?= null,
    val registerDate:String ?= null,
    val image:String ?= null,
    val ownerUID:String ?= null,
    val nfcuid:String ?= null,
    val explanation:String?=null
)