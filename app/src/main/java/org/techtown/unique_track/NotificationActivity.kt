package org.techtown.unique_track

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import org.techtown.unique_track.adapter.NotificationAdapter
import org.techtown.unique_track.model.NotificationData
import org.techtown.unique_track.R.layout.*

class NotificationActivity  : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var myDataset : ArrayList<NotificationData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_notification)
        recyclerView = findViewById<RecyclerView>(R.id.notification_View)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        myDataset= arrayListOf<NotificationData>()
        loadItemData()
    }

    private fun loadItemData(){
        database= FirebaseDatabase.getInstance().getReference("Alerts")    // 가져오려는 data가 있는 firebase path

        database.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(itemSnapshot in snapshot.children){

                        var alert_list=itemSnapshot.getValue(NotificationData::class.java)
                        //alert_list!!.alertName = itemSnapshot.key
                        if(uid==alert_list!!.recieverUID)
                            myDataset.add(alert_list!!)
                    }
                    recyclerView.adapter= NotificationAdapter(myDataset)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}