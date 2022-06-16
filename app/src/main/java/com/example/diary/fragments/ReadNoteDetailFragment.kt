package com.example.diary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.diary.databinding.FragmentReadNoteDetailBinding
import com.example.diary.models.Note
import com.example.diary.viewmodels.ReadNoteViewModel
import java.util.*
import androidx.lifecycle.Observer

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ReadNoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentReadNoteDetailBinding
    private lateinit var note: Note

    private val readNoteViewModel: ReadNoteViewModel by lazy {
        ViewModelProvider(this).get(ReadNoteViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val noteId: UUID = arguments?.getSerializable(ARG_PARAM1) as UUID
        note = Note()
        readNoteViewModel.loadNote(noteId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReadNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readNoteViewModel.noteLiveData.observe (
            viewLifecycleOwner,
            Observer { note ->
                note?.let {
                    this.note = note
                    updateUI()
                }
            }
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(noteId: UUID): ReadNoteDetailFragment {
            return ReadNoteDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, noteId)
                }
            }
        }
    }

    private fun updateUI() {
        binding.titleTv.text = note.title
        binding.descriptionTv.text = note.noteText
        binding.dateCreateNoteTv.text = note.date.toString()
    }

}