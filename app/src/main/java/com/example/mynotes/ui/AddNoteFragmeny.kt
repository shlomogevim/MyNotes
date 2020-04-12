package com.example.mynotes.ui

import android.os.Bundle
import android.text.Editable
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.example.mynotes.R
import com.example.mynotes.room.Note
import com.example.mynotes.room.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note_fragmeny.*
import kotlinx.coroutines.launch


class AddNoteFragmeny : BaseFragment() {

    private var note: Note? = null

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_add_note_fragmeny, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            note = AddNoteFragmenyArgs.fromBundle(it).note
            titleET.setText(note?.title)
            bodyET.setText(note?.body)
        }
        buttonSave.setOnClickListener {
            val noteTitle = titleET.text.toString().trim()
            val noteBody = bodyET.text.toString().trim()
            if (noteTitle.isNullOrEmpty()) {
                titleET.error = "Title is missing"
                return@setOnClickListener
            }
            if (noteBody.isNullOrEmpty()) {
                bodyET.error = "Body is missing"
                return@setOnClickListener
            }
            val newNote = Note(noteTitle, noteBody)

            launch {
                if (note==null) {

                    saveNote(newNote)
                }else{
                    updateNote(newNote)
                }
            }
            navigateBack()
        }
    }

    private suspend fun updateNote(newNote:Note){
        newNote.id= note!!.id
        context?.let {
            NoteDatabase(it).getNodeDao().updateNote(newNote)
            Toast.makeText(it, " note update", Toast.LENGTH_LONG).show()

        }
    }

    private suspend fun saveNote(note: Note) {
        context?.let {
            NoteDatabase(it).getNodeDao().addNote(note)
            Toast.makeText(it, " note save", Toast.LENGTH_LONG).show()
        }

    }

    private fun navigateBack() {
        val action = AddNoteFragmenyDirections.actionAddNoteFragmenyToHomeFragment()
        Navigation.findNavController(buttonSave).navigate(action)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.note_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.deleteNote->note?.let {
                deleteNote()
            }
        }
        return super.onOptionsItemSelected(item)

    }

    private fun deleteNote() {
        context?.let {
            AlertDialog.Builder(it).apply {
                setTitle("Are you sure ?")
                setMessage("You cannot undo this operation")
                setPositiveButton("OK"){
                    dialog, which ->
                    launch {
                        NoteDatabase(it).getNodeDao().deleteNote(note!!)
                        navigateBack()
                    }
                }
                setNegativeButton("Cancel"){
                    dialog, which ->
                }
                show()
            }
        }
    }
}
