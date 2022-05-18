package org.techtown.unique_track.adapter

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.unique_track.ListActivity
import org.techtown.unique_track.ProfileActivity
import org.techtown.unique_track.R
import org.techtown.unique_track.model.ItemData

//Adapter 클래스 생성
class MyAdapter(private val dataset: ArrayList<ItemData>):RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    //ViewHolder 생성
    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        //item.xml에서 정의한 ID item_name을 사용하여 뷰를 할당 (layout과 연결)
        val textView1 : TextView = view.findViewById(R.id.item_name)
        val textView2 : TextView = view.findViewById(R.id.item_date)
        val imageView : ImageView =view.findViewById(R.id.item_image)
        fun setOnClickListener(context : Context, itemText : String){
            view.setOnClickListener {
                var dialogIntent = Intent(context, ProfileActivity::class.java)
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                dialogIntent.putExtra("id", itemText)
                context.startActivity(dialogIntent)
            }

        }

    }

    /* onCreateViewHolder() : 두 매개변수(parent, viewType)를 사용해 ViewHolder를 반환
    parent : 새 item view가 하위요소로 사용되어 연결되는 뷰 그룹 */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //create a new view
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ViewHolder(adapterLayout)
    }

    //onBindViewHolder() : 위치를 기반으로 dataset에서 올바른 ItemData 객체를 찾음
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView1.text=item.ProductName      // item_name
        holder.textView2.text=item.RegisterDate     // item_date
        //image url -> image

        holder.apply{
            Glide.with(imageView.context)                //view,fragement,activity로 부터 context 가져옴
                .load(item.Image)       //
                .into(imageView)        //이미지를 보여줄 view 지정
        }                                           // item_image
        holder.setOnClickListener(holder.itemView.context, holder.textView1.text.toString())
    }

    //getItemCount() : dataset의 크기를 반환
    override fun getItemCount(): Int {
        return dataset.size
    }
}