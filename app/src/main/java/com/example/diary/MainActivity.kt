package com.example.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.diary.databinding.ActivityMainBinding
import com.example.diary.fragments.EditingNoteDetailFragment
import com.example.diary.fragments.NotesListFragment
import com.example.diary.fragments.ReadNoteDetailFragment
import java.util.*

class MainActivity : AppCompatActivity(), NotesListFragment.Callbacks {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragments_container)
        if (currentFragment == null) {
            val newFragment = NotesListFragment.newInstance()
            supportFragmentManager.
            beginTransaction().
            add(R.id.fragments_container, newFragment).
            commit()
        }

    }

    override fun onSelectedNoteForRead(noteId: UUID) {
        val newFragmentForReadNote: ReadNoteDetailFragment = ReadNoteDetailFragment.newInstance(noteId)
        supportFragmentManager.
            beginTransaction().
            replace(R.id.fragments_container, newFragmentForReadNote).
            addToBackStack(null).
            commit()
    }

    override fun onSelectedNoteForUpdate(noteId: UUID) {
        val newFragmentForUpdateNote: EditingNoteDetailFragment = EditingNoteDetailFragment.newInstance(noteId)
        supportFragmentManager.
            beginTransaction().
            replace(R.id.fragments_container, newFragmentForUpdateNote).
            addToBackStack(null).
            commit()
    }


}