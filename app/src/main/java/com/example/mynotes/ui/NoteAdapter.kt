package com.example.mynotes.ui

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.room.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(val noteList:List<Note>):
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflator=LayoutInflater.from(parent.context)
        val view=inflator.inflate(R.layout.item_note,parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount()=noteList.size


    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        holder.view.titleTV.text=noteList[position].title
        holder.view.bodyTV.text=noteList[position].body
        holder.view.setOnClickListener {
            val action=HomeFragmentDirections.actionHomeFragmentToAddNoteFragmeny()
            action.note=noteList[position]
            Navigation.findNavController(it).navigate(action)
        }
    }

    class NoteViewHolder(val view:View):RecyclerView.ViewHolder(view)

}