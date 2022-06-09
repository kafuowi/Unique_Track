package org.techtown.unique_track

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_new_item.*
import org.techtown.unique_track.Fragment.FragmentMainProfile
import org.techtown.unique_track.model.ItemData
import java.time.LocalDate


class NewItemActivity : AppCompatActivity(),FragmentMainProfile.OnDataPassListener {
    private var auth : FirebaseAuth? = null
    private lateinit var database: DatabaseReference
    var  NFCcode : String? = null
    var ImageURL :String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        database = Firebase.database.reference

        setContentView(R.layout.activity_new_item)
        val codeintent = getIntent()

        NewNFCcodeView.text = "NFC Not Found"

        if(codeintent.hasExtra("NFCcode")) {
            NFCcode = codeintent.getStringExtra("NFCcode")


            NewNFCcodeView.text = NFCcode
            Toast.makeText(this, "NFC ID: $NFCcode", Toast.LENGTH_SHORT).show()
        }
        else{
            NewNFCcodeView.text = "NFC Not Found"
        }
        val editTrue = codeintent.getBooleanExtra("editTrue",false)




        //이미지업로드
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        supportFragmentManager.beginTransaction().replace(R.id.ImageUploadLayout,FragmentMainProfile()).commit()

        //home버튼 -> main창으로
        val home_button3 =findViewById<Button>(R.id.home_button3)
        home_button3.setOnClickListener{
            startActivity(Intent(this@NewItemActivity,MainActivity::class.java))
        }
        //back 버튼 -> 이전 창(NFC인식창으로)
        val back_button=findViewById<Button>(R.id.back_button)
        back_button.setOnClickListener{
            finish()
            //startActivity(Intent(this@NewItemActivity,NewNFCActivity::class.java))
        }
        val nfcdata= FirebaseDatabase.getInstance().getReference("Products").child(NFCcode!!)
        nfcdata.addValueEventListener(object: ValueEventListener {
            // snapshot : get database(products)
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!(snapshot.exists()) or (editTrue)) {


                    //Toast.makeText(this@NewItemActivity, (!it.exists()).toString()+editTrue.toString(), Toast.LENGTH_SHORT).show()
                    UploadButton.setOnClickListener {
                        writeNewProduct(
                            TextExplanation.text.toString(),
                            ImageURL,
                            auth!!.uid.toString(),
                            TextProductName.text.toString(),
                            LocalDate.now().toString()
                        )
                        val newintent = Intent(this@NewItemActivity, MainActivity::class.java)
                        startActivity(newintent)
                        finish()
                    }
                } else {

                    Toast.makeText(this@NewItemActivity, "이미 존재하는 태그입니다.", Toast.LENGTH_SHORT)
                        .show()
                    val newintent = Intent(this@NewItemActivity, MainActivity::class.java)
                    startActivity(newintent)
                    finish()

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", error.toException())
            }
        })
    }
    data class Product(val Explanation: String? = null, val Image: String? = null, val OwnerUID: String? = null, val ProductName: String? = null, val RegisterDate: String? = null,val NFCUID: String?) {
        // Null default values create a no-argument default constructor, which is needed
        // for deserialization from a DataSnapshot.
    }
    fun writeNewProduct(Explanation: String?,Image: String?,OwnerUID: String?,ProductName: String?,RegisterDate: String?) {
        val product = ItemData(productName = ProductName, registerDate = RegisterDate, image = Image, ownerUID = OwnerUID, nfcuid = NFCcode, explanation = Explanation)
        NFCcode?.let { database.child("Products").child(it).setValue(product) }
    }

    override fun onDataPass(data: String) {
        Toast.makeText(this, "Image URL: $data", Toast.LENGTH_SHORT).show()
        Log.d("pass", ""+ data)
        ImageURL = data
    }

}