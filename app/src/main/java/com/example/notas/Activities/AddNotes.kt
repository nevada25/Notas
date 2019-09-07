package com.example.notas.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notas.R
import com.example.notas.adapters.dbAdapter
import com.example.notas.goToActivity
import com.example.notas.models.Notes
import com.example.notas.toast
import kotlinx.android.synthetic.main.activity_add_notes.*

class AddNotes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        buttonAddAndUpd.setOnClickListener {
            val title = Title.text.toString()
            val description = Description.text.toString()
            if (isValidTitleAndDescription(title, description)) {
                AddNotes(title, description)
                goToActivity<ListNotesActivity> {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            } else {
                toast("El Campo Titulo y El campo Descripcion son requeridos")
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        goToActivity<ListNotesActivity> {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    }

    private fun AddNotes(title: String, description: String) {
        val dbHandler = dbAdapter(this, null)
        val note = Notes(title, description)
        dbHandler.addNotes(note)
        Toast.makeText(
            this,
            title.toUpperCase() + " SE AGREGO CORRECTAMENTE A LA BASE DE DATOS",
            Toast.LENGTH_LONG
        ).show()
    }


    private fun isValidTitleAndDescription(title: String, description: String): Boolean {
        return !title.isNullOrEmpty() &&
                !description.isNullOrEmpty()
    }
}
