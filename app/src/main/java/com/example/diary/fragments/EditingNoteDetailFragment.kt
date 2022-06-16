package com.example.diary.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.example.diary.R
import com.example.diary.databinding.FragmentEditingNoteDetailBinding
import com.example.diary.models.Note
import com.example.diary.viewmodels.UpdateNoteViewModel
import java.util.*



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EditingNoteDetailFragment : Fragment() {

    private val updateNoteViewModel: UpdateNoteViewModel by lazy {
        ViewModelProvider(this).get(UpdateNoteViewModel::class.java)
    }

    private lateinit var binding: FragmentEditingNoteDetailBinding
    private lateinit var note: Note
    private var cont: Context? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        cont = context
    }

    override fun onDetach() {
        super.onDetach()
        cont = null
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val noteId = arguments?.getSerializable(ARG_PARAM1) as UUID
        note = Note()
        updateNoteViewModel.loadNote(noteId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditingNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateNoteViewModel.noteLiveData.observe(
            viewLifecycleOwner,
            Observer { note ->
                note?.let {
                    this.note = note
                    updateUI()
                }
            }
        )

    }

    override fun onStart() {
        super.onStart()

        binding.saveChangesBtn.setOnClickListener {
            saveChanges()
            Toast.makeText(cont, R.string.save_changes_toast, Toast.LENGTH_LONG).show()
        }

        val textWatcherForTitle = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                note.title = s.toString()
            }
            override fun afterTextChanged(s: Editable?) { }
        }

        val textWatcherForDesc = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                note.noteText = s.toString()
            }
            override fun afterTextChanged(s: Editable?) { }
        }

        binding.titleEt.addTextChangedListener(textWatcherForTitle)
        binding.descriptionEt.addTextChangedListener(textWatcherForDesc)

    }

    override fun onStop() {
        super.onStop()
        saveChanges()
    }

    companion object {
        @JvmStatic
        fun newInstance(noteId: UUID): EditingNoteDetailFragment {
            return EditingNoteDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, noteId)
                }
            }
        }
    }

    private fun updateUI() {
        binding.titleEt.setText(note.title)
        binding.descriptionEt.setText(note.noteText)
        binding.dateCreateNoteTv.text = note.date.toString()

        binding.chooseDateBtn.text = DateFormat.getDateFormat(cont).format(note.date)
        binding.chooseTimeBtn.text = DateFormat.getTimeFormat(cont).format(note.date)
    }

    private fun saveChanges() {
        updateNoteViewModel.saveNote(note)
    }

}