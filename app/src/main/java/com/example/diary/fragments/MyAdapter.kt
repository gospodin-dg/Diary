package com.example.diary.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.R
import com.example.diary.models.Note


class MyAdapter(private val listNotes: List<Note>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val titleNote: TextView = view.findViewById(R.id.note_title)
        private val createNoteDate: TextView = view.findViewById(R.id.note_create_date)

        fun bind(note: Note) {
            titleNote.text = note.title
            createNoteDate.text = note.date.toString()
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