package com.satria.dicoding.latihan.roomapp.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.satria.dicoding.latihan.roomapp.db.Note
import com.satria.dicoding.latihan.roomapp.repository.NoteRepository

class NoteManageViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun insert(note: Note) {
        mNoteRepository.insert(note)
    }

    fun update(note: Note) {
        mNoteRepository.update(note)
    }

    fun delete(note: Note) {
        mNoteRepository.delete(note)
    }
}
