package com.satria.dicoding.latihan.roomapp.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.satria.dicoding.latihan.roomapp.databinding.ItemNoteBinding
import com.satria.dicoding.latihan.roomapp.db.Note
import com.satria.dicoding.latihan.roomapp.helper.NoteDiffCallback
import com.satria.dicoding.latihan.roomapp.ui.insert.NoteManagerActivity

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private val listnotes = ArrayList<Note>()
    fun setListNotes(listNotes: List<Note>) {
        val diffCallback = NoteDiffCallback(this.listnotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listnotes.clear()
        this.listnotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvItemTitle.text = note.title
                tvItemDate.text = note.date
                tvItemDescription.text = note.description
                cvItemNote.setOnClickListener {
                    val intent = Intent(it.context, NoteManagerActivity::class.java)
                    intent.putExtra(NoteManagerActivity.EXTRA_NOTE, note)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listnotes[position])
    }

    override fun getItemCount(): Int = listnotes.size
}