package com.example.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.diary.databinding.ActivityMainBinding
import com.example.diary.fragments.NotesListFragment

class MainActivity : AppCompatActivity() {

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
}