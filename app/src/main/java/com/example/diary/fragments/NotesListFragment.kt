package com.example.diary.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.R
import com.example.diary.databinding.FragmentNotesListBinding
import com.example.diary.models.Note
import com.example.diary.viewmodels.NoteListViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NotesListFragment : Fragment() {

    private lateinit var binding: FragmentNotesListBinding
    private val myViewModel: NoteListViewModel by lazy {
        ViewModelProvider(this).get(NoteListViewModel::class.java)
    }

    private lateinit var recyclerView: RecyclerView
    private var myAdapter: MyAdapter? = MyAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesListBinding.inflate(inflater, container, false)
        recyclerView = binding.recViewNotesList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = myAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel.notesListLiveData.observe(
            viewLifecycleOwner,
            Observer { notes ->
               notes?.let {
                   updateUI(notes)
               }
            }
        )
    }

    companion object {
        fun newInstance(): NotesListFragment {
           return NotesListFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.notes_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.add_note -> {
                createNote()
                return true
            }
            /*R.id.delete_note -> myViewModel.deleteNote()*/
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private  fun updateUI(notes: List<Note>) {
        val adapter = MyAdapter(notes)
        recyclerView.adapter = adapter
    }

    private fun createNote() {
        val note: Note = Note()
        myViewModel.addNote(note)
    }

}