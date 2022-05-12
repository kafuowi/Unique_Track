package org.techtown.unique_track

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import org.techtown.unique_track.adapter.MyAdapter
import org.techtown.unique_track.data.Datasource

class ListActivity : AppCompatActivity() {

    //var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        //firestore= FirebaseFirestore.getInstance()

        //Initialize data
        val myDataset= Datasource().loadItemData()       // datasource파일에서 데이터를 받아와 recyclerviewadapter의 init함수처럼 구현해야함


        val recyclerView=findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter=MyAdapter(this,myDataset)

        //content에서의 변화가 recyclerview의 레이아웃 크기를 변화하지 않을때 성능을 향상시켜줌
        recyclerView.setHasFixedSize(true)
    }

}