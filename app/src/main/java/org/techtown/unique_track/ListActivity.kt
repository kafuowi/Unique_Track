package org.techtown.unique_track

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import org.techtown.unique_track.adapter.MyAdapter
import org.techtown.unique_track.data.Datasource

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        //Initialize data
        val myDataset=Datasource().loadItemData()

        val recyclerView=findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter=MyAdapter(this,myDataset)

        //content에서의 변화가 recyclerview의 레이아웃 크기를 변화하지 않을때 성능을 향상시켜줌
        recyclerView.setHasFixedSize(true)
    }

}