package com.example.diary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.R
import com.example.diary.models.Note

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NotesListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var myAdapter: MyAdapter? = MyAdapter(emptyList())

    val notesTestList = listOf<Note>(
        Note(0, "Понедельник", "шфыпврфо"),
        Note(1, "Вторник", "ваиыв"),
        Note(2, "Среда", "швяаиви"),
        Note(3, "Четверг", "шивчапваирфо"),
        Note(4, "Пятница", "шфролрпло"),
        Note(5, "Суббота", "шффеапкуфвпо"),
        Note(6, "Воскресенье", "шооооооооооооофо")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_notes_list, container, false)
        recyclerView = view.findViewById(R.id.rec_view_notes_list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        myAdapter = MyAdapter(notesTestList)
        recyclerView.adapter = myAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(): NotesListFragment {
           return NotesListFragment()
        }
    }
}