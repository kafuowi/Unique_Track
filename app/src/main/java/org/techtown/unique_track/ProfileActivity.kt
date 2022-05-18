package org.techtown.unique_track

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*
import org.techtown.unique_track.R.layout.activity_profile
import org.techtown.unique_track.adapter.MyAdapter
import org.techtown.unique_track.model.TransferData


class ProfileActivity : AppCompatActivity(){
    private var auth : FirebaseAuth? = null
    private lateinit var database: DatabaseReference
    private lateinit var myDataset : ArrayList<TransferData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_profile)
        var context = this.baseContext
        auth = Firebase.auth
        var textview = findViewById<TextView>(R.id.textView8)
        var intent1 = getIntent()
        database = FirebaseDatabase.getInstance().getReference("Products").child("양도내역")
        database.addValueEventListener(object:ValueEventListener{
            // snapshot : get database(products)
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for (itemSnapshot in snapshot.children) {
                        val item_list = itemSnapshot.getValue(TransferData::class.java)
                        myDataset.add(item_list!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        textview.setText(myDataset[0].UID + myDataset[0].Date)
        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30F)
    }
}