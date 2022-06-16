package com.example.diary.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
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
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val OPEN = 101
private const val UPDATE = 102
private const val CREATE = 100
private const val DELETE = 103

class NotesListFragment : Fragment() {

    private lateinit var binding: FragmentNotesListBinding
    private val myViewModel: NoteListViewModel by lazy {
        ViewModelProvider(this).get(NoteListViewModel::class.java)
    }

    private lateinit var recyclerView: RecyclerView
    private var myAdapter: MyAdapter? = MyAdapter(emptyList())
    private var selectedNote: Note? = null

    private var callback: Callbacks? = null

    interface Callbacks {
        fun onSelectedNoteForRead(noteId: UUID)
        fun onSelectedNoteForUpdate(noteId: UUID)
    }

    companion object {
        fun newInstance(): NotesListFragment {
            return NotesListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

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





    private inner class MyAdapter(private val listNotes: List<Note>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        private inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
            private lateinit var note: Note
            private val titleNote: TextView = view.findViewById(R.id.note_title)
            private val createNoteDate: TextView = view.findViewById(R.id.note_create_date)
            private var selectedItemCode: Int = -1


            init {
                itemView.setOnCreateContextMenuListener(this)
                itemView.setOnClickListener(this)
            }

            fun bind(note: Note) {
                this.note = note
                titleNote.text = this.note.title
                createNoteDate.text = this.note.date.toString()
            }

            override fun onClick(v: View?) {
                if (selectedItemCode == -1) {
                    selectedNote = note
                    v?.setBackgroundColor(resources.getColor(R.color.light_grey))
                    selectedItemCode = 1
                    notifyDataSetChanged()
                } else {
                    selectedNote = null
                    selectedItemCode = -1
                    v?.setBackgroundColor(resources.getColor(R.color.white))
                    notifyDataSetChanged()
                }
            }

            override fun onCreateContextMenu(
                menu: ContextMenu?,
                v: View?,
                menuInfo: ContextMenu.ContextMenuInfo?
            ) {
                menu?.setHeaderTitle(R.string.context_menu_note_title)
                menu?.add(Menu.NONE, OPEN, Menu.NONE, R.string.read_note_menu_item)?.setOnMenuItemClickListener(this)
                menu?.add(Menu.NONE, UPDATE, Menu.NONE, R.string.update_note_menu_item)?.setOnMenuItemClickListener(this)
                menu?.add(Menu.NONE, DELETE, Menu.NONE, R.string.delete_note_menu_item)?.setOnMenuItemClickListener(this)
            }

            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId) {
                    OPEN -> {
                        callback?.onSelectedNoteForRead(note.id)
                    }
                    UPDATE -> {
                        callback?.onSelectedNoteForUpdate(note.id)
                    }
                    DELETE -> {
                        deleteNoteSelected(note)
                        return true
                    }
                }
                return true
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val note = listNotes[position]
            holder.bind(note)
        }

        override fun getItemCount(): Int {
            return listNotes.size
        }

    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.notes_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.add_note -> {
                val note: Note = Note()
                createNote(note)
                callback?.onSelectedNoteForUpdate(note.id)
                return true
            }
            R.id.delete_note -> {
                selectedNote?.let { deleteNoteSelected(it) }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    private  fun updateUI(notes: List<Note>) {
        val adapter = MyAdapter(notes)
        recyclerView.adapter = adapter
    }

    private fun createNote(note: Note) {
        myViewModel.addNote(note)
    }

    private fun deleteNoteSelected(note: Note) {
        myViewModel.deleteNote(note)
    }

}