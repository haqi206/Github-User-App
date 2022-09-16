package com.example.submission2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission2.database.UserEntity
import com.example.submission2.databinding.ItemRowFavBinding


class FavoriteAdapter() : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {
    val list = ArrayList<UserEntity>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemRowFavBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = ItemRowFavBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = list[position]
        holder.binding.tvItemUsername.text = user.userName
        Glide.with(holder.itemView.context)
            .load(user.photo)
            .circleCrop()
            .into(holder.binding.imgItemPhoto)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(list[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = list.size

    fun listFavorite(listArray: List<UserEntity>){
        this.list.clear()
        this.list.addAll(listArray)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserEntity)
    }
}