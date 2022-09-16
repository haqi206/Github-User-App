package com.example.submission2.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission2.DetailActivity
import com.example.submission2.api.FollowersResponseItem
import com.example.submission2.databinding.ItemRowFollowBinding

class FollowersAdapter(private val listUser: List<FollowersResponseItem>) : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    class FollowersViewHolder(var binding: ItemRowFollowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val itemView = ItemRowFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        val user = listUser[position]
        holder.binding.tvItemUsername.text = user.login
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .circleCrop()
            .into(holder.binding.imgItemPhoto)
    }

    override fun getItemCount(): Int = listUser.size
}