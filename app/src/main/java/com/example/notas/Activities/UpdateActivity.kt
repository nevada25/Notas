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
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val idExtra = intent.getStringExtra("id")
        val titleExtra = intent.getStringExtra("title")
        val DescExtre = intent.getStringExtra("description")

        TitleUpd.setText(titleExtra.toString())
        DescriptionUpd.setText(DescExtre.toString())

        buttonUpd.setOnClickListener {
            val title = TitleUpd.text.toString()
            val description = DescriptionUpd.text.toString()
            if (isValidTitleAndDescription(title, description)) {
                UdpNotes(idExtra.toInt(), title, description)
                goToActivity<ListNotesActivity> {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            } else {
                toast("El Campo Titulo y El campo Descripcion son requeridos")
            }
        }

    }


    private fun UdpNotes(id: Int, title: String, description: String) {
        val dbHandler = dbAdapter(this, null)
        val note = Notes(id, title, description)
        dbHandler.UdpNotes(note)
        Toast.makeText(
            this,
            title.toUpperCase() + " SE ACTUALIZO CORRECTAMENTE EN LA BASE DE DATOS",
            Toast.LENGTH_LONG
        ).show()
    }


    private fun isValidTitleAndDescription(title: String, description: String): Boolean {
        return !title.isNullOrEmpty() &&
                !description.isNullOrEmpty()
    }
}
