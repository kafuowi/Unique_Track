package org.techtown.unique_track.adapter

import android.app.Notification
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.unique_track.ProfileActivity
import org.techtown.unique_track.R
import org.techtown.unique_track.TransferActivity
import org.techtown.unique_track.model.NotificationData
import java.util.concurrent.TransferQueue

class NotificationAdapter (private val dataset: ArrayList<NotificationData>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder> () {
    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val textView1 : TextView = view.findViewById(R.id.notification_name)
        val textView2 : TextView = view.findViewById(R.id.notification_sender)
        val imageView : ImageView = view.findViewById(R.id.notification_image)

        fun onClick(){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.notification,parent,false)

        return NotificationAdapter.ViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]

        holder.textView1.text=item.productName
        holder.textView2.text=item.senderUID

        //image url -> image
        holder.apply{
            Glide.with(imageView.context)
                .load(item.image)
                .into(imageView)
        }
        // item_image
        holder.itemView.setOnClickListener {
            holder.onClick()
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}