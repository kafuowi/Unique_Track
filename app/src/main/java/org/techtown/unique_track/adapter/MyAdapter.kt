package org.techtown.unique_track.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.unique_track.R
import org.techtown.unique_track.model.ItemData

//Adapter 클래스 생성
class MyAdapter(val context : Context, val dataset: List<ItemData>):RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    //ViewHolder 생성
    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        //item.xml에서 정의한 ID item_name, item_date, item_imaage를 사용하여 뷰를 할당
        val textView1 : TextView = view.findViewById(R.id.item_name)
        val textView2 : TextView = view.findViewById(R.id.item_date)
        val imageView : ImageView = view.findViewById(R.id.item_image)
    }

    /* onCreateViewHolder() : 두 매개변수(parent, viewType)를 사용해 ViewHolder를 반환
    parent : 새 item view가 하위요소로 사용되어 연결되는 뷰 그룹 */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //create a new view
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)

        return ViewHolder(adapterLayout)
    }

    //onBindViewHolder() : view와 실제 데이터 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView1.text = dataset[position].itemName   //item_name
        holder.textView2.text = dataset[position].date       //item_date
        holder.apply{
            Glide.with(context).load(dataset[position].imgurl)
                .into(imageView)
        }
    }

    //getItemCount() : dataset의 크기를 반환
    override fun getItemCount(): Int {
        return dataset.size
    }
}