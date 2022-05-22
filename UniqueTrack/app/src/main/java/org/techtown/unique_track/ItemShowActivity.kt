package org.techtown.unique_track

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_iteminfo.*

class ItemShowActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    private lateinit var database: DatabaseReference
    private val arr = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iteminfo)
        auth = Firebase.auth

        var NFCuid = getIntent().getStringExtra("NFCuid")
        var notNullableNFCuid : String = NFCuid!!

        database = FirebaseDatabase.getInstance().getReference().child("Products").child(notNullableNFCuid)

        val productListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var text = findViewById<TextView>(R.id.InformationText)
                val productName = snapshot.child("productName").getValue<String>()
                InformationText.append("ProductName: "+productName+"\n")
                val ownerUID = snapshot.child("ownerUID").getValue<String>()
                InformationText.append("OwnerUID: "+ownerUID+"\n")
                val ownerName = snapshot.child("ownerName").getValue<String>()
                InformationText.append("OwnerName: "+ownerName+"\n")
                val registerDate = snapshot.child("registerDate").getValue<String>()
                InformationText.append("RegisterDate: "+registerDate+"\n")
                val explanation = snapshot.child("explanation").getValue<String>()
                InformationText.append("Explanation: "+explanation)
            }

            override fun onCancelled(error: DatabaseError) {
                InformationText.setText("Error: "+error.toException())
            }
        }
        database.addValueEventListener(productListener)
    }

}