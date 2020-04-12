package com.example.mynotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.R
import com.example.mynotes.room.NoteDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        buttonAdd_LocateInHomeFragment.setOnClickListener {
            val action=
                HomeFragmentDirections.actionHomeFragmentToAddNoteFragmeny()
            Navigation.findNavController(it).navigate(action)
        }
        notesRV.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(context)
        }
        launch {
            context?.let {
                val notes=NoteDatabase(it).getNodeDao().getAllNotes()
                notesRV.adapter=NoteAdapter(notes)
            }
        }
    }

}
