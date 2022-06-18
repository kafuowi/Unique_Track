package org.techtown.unique_track

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_iteminfo.*
import org.techtown.unique_track.adapter.MyAdapter
import org.techtown.unique_track.model.ItemData
import org.techtown.unique_track.model.User
import java.lang.Exception

class ItemShowActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    private lateinit var database: DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iteminfo)
        auth = Firebase.auth

        //home버튼 -> main창으로
        val home_button5 =findViewById<Button>(R.id.home_button5)
        home_button5.setOnClickListener{
            startActivity(Intent(this@ItemShowActivity,MainActivity::class.java))
        }
        //back 버튼 -> 이전 창(NFC인식창으로)
        val back_button3=findViewById<Button>(R.id.back_button3)
        back_button3.setOnClickListener{
            //startActivity(Intent(this@ItemShowActivity,NFCActivity::class.java))
            finish()

        }


        var NFCuid = getIntent().getStringExtra("NFCuid")
        var notNullableNFCuid : String = NFCuid!!

        database = FirebaseDatabase.getInstance().getReference()
        loadItemData(NFCuid)
    }

    private fun loadItemData(NFCid: String){
        database= FirebaseDatabase.getInstance().getReference("Products").child(NFCid)    // 가져오려는 data가 있는 firebase path

        database.addValueEventListener(object:ValueEventListener{
            // snapshot : get database(products)
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val item=snapshot.getValue(ItemData::class.java)
                    if(item?.ownerUID == auth!!.uid) {
                        itemEditButton.setOnClickListener {
                            val newintent = Intent(this@ItemShowActivity, NewItemActivity::class.java)
                            newintent.putExtra("NFCcode", item?.nfcuid)
                            newintent.putExtra("editTrue", true)
                            startActivity(newintent)
                            finish()
                        }
                    }
                    else{
                        itemEditButton.visibility = View.GONE
                    }
                    var databaseuser = FirebaseDatabase.getInstance().getReference("Owners").child(item?.ownerUID!!)
                    databaseuser.addValueEventListener(object:ValueEventListener{
                        // snapshot : get database(products)
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {

                                val tempuser=snapshot.getValue(User::class.java)
                                var username = tempuser?.username!!

                                OwnerNameText.append(username)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.w(ContentValues.TAG, "loadPost:onCancelled", error.toException())
                        }
                    }
                    )

                    //owner 이름 가져오기

                // 현재 사용자의 uid와 firebase의 item_list의 ownerUID가 같을때만 myDataset에 데이터 추가
                    ProductNameText.append(item?.productName)
                    RegisterDateText.append(item?.registerDate)
                    NFCuidText.append(item?.nfcuid)
                    ExplanationText.append(item?.explanation)
                    OwnerUIDText.append(item?.ownerUID)
                    val imageView = findViewById<ImageView>(R.id.editItemImage)
                    // Create a storage reference from our app
                    val storageRef = FirebaseStorage.getInstance().reference
                    val head_len = "https://firebasestorage.googleapis.com/v0/b/unique-track-f112a.appspot.com/o/images%2F"
                        .length
                    //Get the image's short name
                    var imageName = item?.image?.substring(head_len)
                    imageName = imageName?.substringBefore("?alt=media&token=ca1ee99a-a96e-4074-9bf8-49eda0d701a4")

                    // Create a reference with an initial file path and name
                    val pathReference = storageRef.child("images/" + imageName)

                    pathReference.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri>{
                        override fun onSuccess(p0: Uri?) {
                            Glide.with(applicationContext).load(p0).override(100, 100).centerCrop().into(imageView)
                        }
                    }).addOnFailureListener(object : OnFailureListener{
                        override fun onFailure(p0: Exception) {
                            Toast.makeText(applicationContext, "실패", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", error.toException())
            }

        })

    }
}