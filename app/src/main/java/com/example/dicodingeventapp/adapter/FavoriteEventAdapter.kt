package com.example.dicodingeventapp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingeventapp.data.local.entity.FavoriteEventsEntity
import com.example.dicodingeventapp.databinding.ItemEventBinding
import com.example.dicodingeventapp.ui.detail_event.DetailEventActivity

class FavoriteEventAdapter: ListAdapter<FavoriteEventsEntity, FavoriteEventAdapter.EventHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventHolder(binding)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class EventHolder(private val binding: ItemEventBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(event: FavoriteEventsEntity){
            // binding data to UI and data from reponse api
             Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.imgEvent)
            binding.titleEvent.text = event.name

            binding.cardViewEvent.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailEventActivity::class.java)
                intent.putExtra(DetailEventActivity.IDEVENT, event.id.toString())
                intent.putExtra(DetailEventActivity.STATUSEVENT, event.statusEvent.toString())
                context.startActivity(intent)
            }
        }
    }

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteEventsEntity> =
            object : DiffUtil.ItemCallback<FavoriteEventsEntity>() {
                override fun areItemsTheSame(oldItem: FavoriteEventsEntity, newItem: FavoriteEventsEntity): Boolean {
                    return oldItem.name == newItem.name
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: FavoriteEventsEntity, newItem: FavoriteEventsEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }
}