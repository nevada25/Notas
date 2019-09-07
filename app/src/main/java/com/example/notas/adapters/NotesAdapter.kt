package com.example.notas.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.notas.R
import com.example.notas.models.Notes
import kotlinx.android.synthetic.main.list_view_note.view.*


class NoteAdapter(val context: Context, val list: List<Notes>) : BaseAdapter() {
    private val mInflator: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val vh: NoteViewHolder
        if (convertView === null) {
            view = mInflator.inflate(R.layout.list_view_note, parent, false)
            vh = NoteViewHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as NoteViewHolder
        }
        vh.id.text = list[position].id.toString()
        vh.title.text = list[position].title.toString()
        return view
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

}

private class NoteViewHolder(view: View) {
    val id: TextView = view.textViewId
    val title: TextView = view.textViewTitle
}