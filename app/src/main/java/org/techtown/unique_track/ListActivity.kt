package org.techtown.unique_track

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import org.techtown.unique_track.adapter.MyAdapter
import org.techtown.unique_track.model.ItemData

class ListActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var myDataset : ArrayList<ItemData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        recyclerView=findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager=LinearLayoutManager(this)

        //content에서의 변화가 recyclerview의 레이아웃 크기를 변화하지 않을때 성능을 향상시켜줌
        recyclerView.setHasFixedSize(true)

        myDataset= arrayListOf<ItemData>()
        loadItemData()

        /* 제품정보페이지창으로 이동 - 제품정보페이지 만들고 연결해주기 ******************** */
        // ...

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
                        myDataset.add(item_list!!)
                    }
                    // recyclerview의 adapter로
                    recyclerView.adapter=MyAdapter(myDataset)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}