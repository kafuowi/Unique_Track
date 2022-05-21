package org.techtown.unique_track

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_new_item.*
import java.time.LocalDate


class NewItemActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    private lateinit var database: DatabaseReference
    var  NFCcode : String? = null

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
        UploadButton.setOnClickListener {
            writeNewProduct(TextExplanation.text.toString(),"default",TextOwnerName.text.toString(),
                auth!!.uid.toString(),TextProductName.text.toString(), LocalDate.now().toString())
            val newintent = Intent(this@NewItemActivity, MainActivity::class.java)
            startActivity(newintent)
            finish()
        }

    }
    data class Product(val Explanation: String? = null, val Image: String? = null, val OwnerName: String? = null, val OwnerUID: String? = null, val ProductName: String? = null, val RegisterDate: String? = null) {
        // Null default values create a no-argument default constructor, which is needed
        // for deserialization from a DataSnapshot.
    }
    fun writeNewProduct(Explanation: String?,Image: String?,OwnerName: String?,OwnerUID: String?,ProductName: String?,RegisterDate: String?) {
        val product = Product(Explanation,Image,OwnerName,OwnerUID,ProductName,RegisterDate)
        NFCcode?.let { database.child("Products").child(it).setValue(product) }
    }

}