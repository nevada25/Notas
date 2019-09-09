package com.example.notas.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notas.R
import com.example.notas.adapters.NoteAdapter
import com.example.notas.adapters.dbAdapter
import com.example.notas.goToActivity
import com.example.notas.models.Notes
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.view.size
import com.example.notas.toast
import kotlinx.android.synthetic.main.activity_list_notes.*
import kotlinx.android.synthetic.main.content_list_notes.*
import kotlin.collections.ArrayList


class ListNotesActivity : AppCompatActivity() {
    private lateinit var adapter: NoteAdapter
    private lateinit var noteList: List<Notes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_notes)
        setSupportActionBar(toolbar)
        mostrarnotas()

        ListViewNotes.setOnItemClickListener { parent, view, position, id ->
            getView(getNotes().get(position))
        }
        fab.setOnClickListener {
            goToActivity<AddNotes> {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }

            ListViewNotes.setOnItemLongClickListener { parent, view, position, id ->

                val builder = AlertDialog.Builder(this@ListNotesActivity)
                val items = arrayOf("Editar", "Eliminar")
                with(builder)
                {
                    setTitle("Acciones")
                    setItems(items) { dialog, which ->
                        if(items[which]==="Eliminar"){
                            getDeleteNote(getNotes().get(position))
                        }else{
                            getUpdate(getNotes().get(position))
                        }
                    }
                    show()
                }


                true

        }

        ListViewNotes.setOnItemLongClickListener { parent, view, position, id ->

            val builder = AlertDialog.Builder(this@ListNotesActivity)
            val items = arrayOf("Editar", "Eliminar")
            with(builder)
            {
                setTitle("Acciones")
                setItems(items) { dialog, which ->
                    if(items[which]==="Eliminar"){
                        getDeleteNote(getNotes().get(position))
                    }else{
                        getUpdate(getNotes().get(position))
                    }
                }
                show()
            }


            true
        }


    }


    fun mostrarnotas() {
        adapter = NoteAdapter(this, getNotes())
        noteList = getNotes()
        ListViewNotes.adapter = adapter
        if(noteList.size==0){
            ocultarLista()
        }else {
            mostrarLista()
        }
    }

    fun getDeleteNote(note: Notes){
        val dbHandler = dbAdapter(this, null)
        dbHandler.deleteNotes(note.id)
        mostrarnotas()
        Toast.makeText(this, "EL ${note.title.toString().toUpperCase()} SE ELIMINO CORRECTAMENTE", Toast.LENGTH_LONG).show()
    }

    fun getUpdate(note: Notes){
       val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra("id", "${note.id}")
        intent.putExtra("title", "${note.title}")
        intent.putExtra("description", "${note.description}")
        startActivity(intent)
    }

    fun getView(note: Notes){
        val intent = Intent(this, ViewNotesActivity::class.java)
        intent.putExtra("id", "${note.id}")
        intent.putExtra("title", "${note.title}")
        intent.putExtra("description", "${note.description}")
        startActivity(intent)
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

    fun mostrarLista(){
        ListViewNotes.isVisible = true
        imageViewVacio.isVisible = false
        textViewMensaje.isVisible= false
    }

    fun ocultarLista(){
        ListViewNotes.isVisible = false
        imageViewVacio.isVisible = true
        textViewMensaje.isVisible= true
    }


}
