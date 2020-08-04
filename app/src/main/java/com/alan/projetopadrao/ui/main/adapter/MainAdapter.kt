package com.alan.projetopadrao.ui.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alan.projetopadrao.R
import com.alan.projetopadrao.data.model.EventItem
import com.alan.projetopadrao.ui.details.view.DetailsActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.event_item.view.*
import java.lang.Exception

class MainAdapter(private val events: List<EventItem>) : RecyclerView.Adapter<ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.let {
            holder.bind(event)
        }

        holder.itemView.layoutEvent.setOnClickListener {
            val intent = Intent(it.context, DetailsActivity::class.java)
            intent.putExtra("id_event", event.id)
            intent.putExtra("latitude", event.latitude)
            intent.putExtra("longitude", event.longitude)
            it.context.startActivity(intent)
        }

        holder.itemView.btnShare.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Evento " + event.title)
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Evento: " + event.title + "\n\nDescrição: \n " + event.description)
            shareIntent.type = "text/plain"
            it.context.startActivity(shareIntent)
        }
    }
}

 class ViewHolder(itemView : View ) : RecyclerView.ViewHolder(itemView){

     fun bind(event: EventItem){
         itemView.apply {
             title.text = event.title
             Picasso.get().load(event.image).into(image, object : Callback{
                 override fun onError(e: Exception?) {
                     Picasso.get()
                         .load(R.drawable.default_image)
                         .into(image)
                 }

                 override fun onSuccess() {}
             })

             txtDate.text = event.date.toString()
             txtPrice.text = event.price.toString()
         }
     }
 }

