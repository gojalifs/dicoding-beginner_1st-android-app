package com.satria.dicoding.latihan.myquote_latihanloopj

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satria.dicoding.latihan.myquote_latihanloopj.databinding.ItemQuoteBinding

class QuoteAdapter(private val listReview: ArrayList<String>) :
    RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemQuoteBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteAdapter.ViewHolder {
        val binding = ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteAdapter.ViewHolder, position: Int) {
        holder.binding.tvItem.text = listReview[position]
    }

    override fun getItemCount(): Int = listReview.size
}