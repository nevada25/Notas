package com.example.notas.adapters

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.notas.models.Notes

class dbAdapter(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(
        context, DATABASE_NAME,
        factory, DATABASE_VERSION
    ) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT" + ")")
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addNotes(note: Notes) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, note.title)
        values.put(COLUMN_DESCRIPTION, note.description)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun UdpNotes(note: Notes) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, note.title)
        values.put(COLUMN_DESCRIPTION, note.description)
        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "$COLUMN_ID=${note.id}", null)
        db.close()
    }


    fun deleteNotes(note: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=${note}", null)
        db.close()
    }


    fun getAllNotes(): Cursor? {
        val db = this.writableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "notes.db"
        val TABLE_NAME = "notes"
        val COLUMN_ID = "idnotes"
        val COLUMN_TITLE = "title"
        val COLUMN_DESCRIPTION = "description"
    }
}