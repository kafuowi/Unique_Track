package org.techtown.unique_track

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference
import org.techtown.unique_track.adapter.MyAdapter
import org.techtown.unique_track.model.ItemData

class ListActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var myDataset : ArrayList<ItemData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val home_button2 =findViewById<Button>(R.id.home_button2)
        home_button2.setOnClickListener{
            startActivity(Intent(this@ListActivity,MainActivity::class.java))
        }

        recyclerView=findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager=LinearLayoutManager(this)

        //content에서의 변화가 recyclerview의 레이아웃 크기를 변화하지 않을때 성능을 향상시켜줌
        recyclerView.setHasFixedSize(true)

        myDataset= arrayListOf<ItemData>()
        loadItemData()


    }
    // firebase의 realtime database에서 데이터를 가져와 myDataset에 저장
    private fun loadItemData(){
        database= FirebaseDatabase.getInstance().getReference("Products")    // 가져오려는 data가 있는 firebase path

        database.addValueEventListener(object:ValueEventListener{
            // snapshot : get database(products)
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(itemSnapshot in snapshot.children){
                        val item_list=itemSnapshot.getValue(ItemData::class.java)
                        // 현재 사용자의 uid와 firebase의 item_list의 ownerUID가 같을때만 myDataset에 데이터 추가
                        if(uid==item_list!!.ownerUID)
                            myDataset.add(item_list!!)
                    }
                    // recyclerview의 adapter로
                    recyclerView.adapter=MyAdapter(myDataset)
                }
            }

            override fun onCancelled(error: DatabaseError) {

                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }

        })
    }

}