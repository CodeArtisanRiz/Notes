package com.t3g.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.Date

class AddEditNoteActivity : AppCompatActivity() {
    lateinit var noteTitleEdt : EditText
    lateinit var noteDescriptionEdt : EditText
    lateinit var addUpdateBtn : Button
    lateinit var viewModal: NoteViewModal
    var noteID = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        noteTitleEdt = findViewById(R.id.idEdtNoteTitle)
        noteDescriptionEdt = findViewById(R.id.idEdtNoteDescription)
        addUpdateBtn = findViewById(R.id.idBtnAddUpdate)
        viewModal= ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteViewModal::class.java]

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")){
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            val noteID = intent.getIntExtra("noteID", -1)
            addUpdateBtn.text = "Update Note"
            noteTitleEdt.setText(noteTitle)
            noteDescriptionEdt.setText(noteDesc)
        } else {
            addUpdateBtn.text = "Save Note"
        }
        addUpdateBtn.setOnClickListener{
            val noteTitle = noteTitleEdt.text.toString()
            val noteDesc = noteDescriptionEdt.text.toString()

            if (noteType.equals("Edit")){
                if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM yyyy - HH:mm")
                    val currentDate:String = sdf.format(Date())
                    val updateNote = Note(noteTitle, noteDesc, currentDate)
                    updateNote.id = noteID
                    viewModal.updateNote(updateNote)
                    Toast.makeText(this, "Note Updated", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM yyyy - HH:mm")
                    val currentDate:String = sdf.format(Date())
                    viewModal.addNote(Note(noteTitle, noteDesc, currentDate))
                    Toast.makeText(this, "Note Saved", Toast.LENGTH_LONG).show()

                }
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        this.finish()
    }
}