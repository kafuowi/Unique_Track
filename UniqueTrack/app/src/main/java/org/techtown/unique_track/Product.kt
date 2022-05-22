package org.techtown.unique_track

data class Product(val explanation: String? = null, val image: String? = null, val ownerName: String? = null,
                   val ownerUID: String? = null, val productName: String? = null, val registerDate: String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}
