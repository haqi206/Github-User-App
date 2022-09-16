package com.example.submission2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission2.api.FollowingResponseItem
import com.example.submission2.databinding.ItemRowFollowingBinding

class FollowingAdapter(private val listUser: List<FollowingResponseItem>) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    class FollowingViewHolder(var binding: ItemRowFollowingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val itemView = ItemRowFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val user = listUser[position]
        holder.binding.tvItemUsername.text = user.login
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .circleCrop()
            .into(holder.binding.imgItemPhoto)
    }

    override fun getItemCount(): Int = listUser.size
}