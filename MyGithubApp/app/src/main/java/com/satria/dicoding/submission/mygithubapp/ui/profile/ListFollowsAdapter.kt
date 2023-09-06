package com.satria.dicoding.submission.mygithubapp.ui.profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satria.dicoding.submission.mygithubapp.MainActivity
import com.satria.dicoding.submission.mygithubapp.data.response.UserResponse
import com.satria.dicoding.submission.mygithubapp.data.view_model.UserViewModel
import com.satria.dicoding.submission.mygithubapp.databinding.ItemUserBinding

class ListFollowsAdapter :
    ListAdapter<UserResponse, ListFollowsAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserResponse>() {
            override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, MainActivity::class.java)
            UserViewModel.USER_ID = user.login ?: ""
            holder.itemView.context.startActivity(intent)
        }
    }

    class MyViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserResponse) {
            binding.tvCardUsername.text = user.login
            Glide.with(itemView.context).load(user.avatarUrl).into(binding.imgCardAvatar)
        }
    }
}