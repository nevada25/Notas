package com.example.notas.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.example.notas.R
import com.example.notas.adapters.dbAdapter
import com.example.notas.goToActivity
import com.example.notas.models.Notes
import com.example.notas.toast
import kotlinx.android.synthetic.main.activity_view_notes.*

class ViewNotesActivity : AppCompatActivity() {
      var extraId: String=""
    var extraTitle: String=""
    var extraDescrip: String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_notes)

       /* val idExtra = intent.getStringExtra("id")
        val titleExtra = intent.getStringExtra("title")
        val descExtra = intent.getStringExtra("description")
        */

         extraId = intent.getStringExtra("id")
        extraTitle = intent.getStringExtra("title")
        extraDescrip = intent.getStringExtra("description")



        TitleV.setText(extraTitle.toString())
        DescriptionV.setText(extraDescrip.toString())

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.context_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.mEdit -> {
                getUpdate(extraId.toInt(),extraTitle,extraDescrip)
                  true
            }
            R.id.mdelete -> {
                getDeleteNote(extraId.toInt(),extraTitle,extraDescrip)
                  true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun getDeleteNote(id: Int, title: String, description : String){
        val dbHandler = dbAdapter(this, null)
        dbHandler.deleteNotes(id)
        goToActivity<ListNotesActivity> {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        Toast.makeText(this, "EL ${title.toUpperCase()} SE ELIMINO CORRECTAMENTE", Toast.LENGTH_LONG).show()
    }

    fun getUpdate(id: Int, title: String, description : String){
        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra("id", "${id}")
        intent.putExtra("title", "${title}")
        intent.putExtra("description", "${description}")
        startActivity(intent)
    }

}
