package com.satria.dicoding.submission.mygithubapp.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satria.dicoding.submission.mygithubapp.data.response.FollowResponseItem
import com.satria.dicoding.submission.mygithubapp.databinding.ItemUserBinding

class ListFollowsAdapter :
    ListAdapter<FollowResponseItem, ListFollowsAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowResponseItem>() {
            override fun areItemsTheSame(
                oldItem: FollowResponseItem,
                newItem: FollowResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FollowResponseItem,
                newItem: FollowResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListFollowsAdapter.ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListFollowsAdapter.ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userItem: FollowResponseItem) {
            binding.tvCardUsername.text = userItem.login
            Glide.with(itemView.context).load(userItem.avatarUrl).into(binding.imgCardAvatar)
        }
    }
}