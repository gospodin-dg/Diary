package com.example.diary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diary.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NotesListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(): NotesListFragment {
           return NotesListFragment()
        }
    }
}