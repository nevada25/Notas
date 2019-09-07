package com.example.notas.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notas.R
import com.example.notas.adapters.NoteAdapter
import com.example.notas.adapters.dbAdapter
import com.example.notas.goToActivity
import com.example.notas.models.Notes
import android.content.Intent
import kotlinx.android.synthetic.main.activity_list_notes.*
import kotlinx.android.synthetic.main.content_list_notes.*


class ListNotesActivity : AppCompatActivity() {
    private lateinit var adapter: NoteAdapter
    private lateinit var noteList: List<Notes>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_notes)
        setSupportActionBar(toolbar)
        mostrarnotas()


        fab.setOnClickListener {
            goToActivity<AddNotes> {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

        }
        ListViewNotes.setOnItemClickListener { _, _, position, _ ->
            val id = getNotes().get(position).id.toString()
            val title: String = getNotes().get(position).title.toString()
            val description: String = getNotes().get(position).description.toString()
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("id", "$id")
            intent.putExtra("title", "$title")
            intent.putExtra("description", "$description")
            startActivity(intent)

        }


    }


    fun mostrarnotas() {
        adapter = NoteAdapter(this, getNotes())
        noteList = getNotes()
        ListViewNotes.adapter = adapter

    }

    private fun getNotes(): ArrayList<Notes> {
        val dbHandler = dbAdapter(this, null)
        val cursor = dbHandler.getAllNotes()
        cursor!!.moveToFirst()
        val notesList: ArrayList<Notes> = arrayListOf<Notes>()
        try {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst()
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        val id = cursor.getInt(cursor.getColumnIndex(dbAdapter.COLUMN_ID))
                        val title = cursor.getString(cursor.getColumnIndex(dbAdapter.COLUMN_TITLE))
                        val description =
                            cursor.getString(cursor.getColumnIndex(dbAdapter.COLUMN_DESCRIPTION))
                        notesList.add(Notes(id, title, description))
                    }

                }
            }
        } finally {
            cursor.close()
        }
        return notesList
    }

}
