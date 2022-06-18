package org.techtown.unique_track

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.techtown.unique_track.model.ItemData
import org.techtown.unique_track.R.layout.*

class TransferActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_transfer)
        var productImage = findViewById<ImageView>(R.id.trans_itemimage)
        var productName = findViewById<TextView>(R.id.trans_productname)
        var senderUID = findViewById<TextView>(R.id.trans_senderUID)
        var transferButton = findViewById<Button>(R.id.transfer_final)
        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        var intent = getIntent()



       database= FirebaseDatabase.getInstance().getReference("Products")
        database.addValueEventListener(object: ValueEventListener {
            // snapshot : get database(products)
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (itemSnapshot in snapshot.children) {
                        val item_list = itemSnapshot.getValue(ItemData::class.java)

                        if (intent!!.getStringExtra("productName") == item_list!!.productName)
                            productName.setText(item_list!!.productName)
                            senderUID.setText(intent!!.getStringExtra("senderUID"))
                            Glide.with(applicationContext).load(item_list!!.image).into(productImage)

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        transferButton.setOnClickListener {
            if(checkBox.isChecked) {
                database.addValueEventListener(object: ValueEventListener {
                    // snapshot : get database(products)
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (itemSnapshot in snapshot.children) {
                                var item_list = itemSnapshot.getValue(ItemData::class.java)
                                var imageString = item_list?.image

                                if (intent!!.getStringExtra("senderUID") == item_list!!.ownerUID){
                                    var newitem = ItemData(item_list.productName,item_list.registerDate,item_list.image,uid,item_list.nfcuid,item_list.explanation)
                                    database.child(item_list?.nfcuid!!).setValue(newitem)
                                    //Toast.makeText(this@TransferActivity,item_list.image,Toast.LENGTH_SHORT).show()
                                    database.parent!!.child("Alerts").child(intent.getStringExtra("key").toString()).removeValue()
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }
    }
}