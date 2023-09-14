package com.satria.dicoding.latihan.roomapp.ui.insert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.satria.dicoding.latihan.roomapp.R
import com.satria.dicoding.latihan.roomapp.databinding.ActivityNoteManagerBinding
import com.satria.dicoding.latihan.roomapp.db.Note
import com.satria.dicoding.latihan.roomapp.helper.DateHelper
import com.satria.dicoding.latihan.roomapp.helper.ViewModelFactory

class NoteManagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteManagerBinding
    private lateinit var noteManageViewModel: NoteManageViewModel
    private var isEdit = false
    private var note: Note? = null

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        noteManageViewModel = obtainViewModel(this)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            isEdit = true
        } else {
            note = Note()
        }

        val actionbarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionbarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (note != null) {
                note?.let {
                    binding.edtTitle.setText(it.title)
                    binding.edtDescription.setText(it.description)
                }
            }
        } else {
            actionbarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionbarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSubmit.text = btnTitle
        binding.btnSubmit.setOnClickListener {
            var title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()
            when {
                title.isEmpty() -> binding.edtTitle.error = getString(R.string.empty)
                description.isEmpty() -> binding.edtDescription.error = getString(R.string.empty)
                else -> {
                    note.let { note ->
                        note?.title = title
                        note?.description = description
                    }
                    if (isEdit) {
                        noteManageViewModel.update(note as Note)
                        showToast(getString(R.string.change))
                    } else {
                        note.let { note ->
                            note?.date = DateHelper.getCurrentDate()
                        }
                        noteManageViewModel.insert(note as Note)
                        showToast(getString(R.string.added))
                    }
                    finish()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogTitle = getString(R.string.delete)
            dialogMessage = getString(R.string.message_delete)
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    noteManageViewModel.delete(note as Note)
                    showToast(getString(R.string.deleted))
                }

                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): NoteManageViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(NoteManageViewModel::class.java)

    }
}