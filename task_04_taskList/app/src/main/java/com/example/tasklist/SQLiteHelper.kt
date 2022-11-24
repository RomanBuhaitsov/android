package com.example.tasklist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE $TABLE_NAME ($NAME_COl TEXT PRIMARY KEY, " +
                "$DESCRIPTION_COL  TEXT, $DATA_COL TEXT, $STATUS_COL TEXT)")
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addEntry(name: String, description: String, data: String, status: String) {
        val values = ContentValues()
        values.put(NAME_COl, name)
        values.put(DESCRIPTION_COL, description)
        values.put(DATA_COL, data)
        values.put(STATUS_COL, status)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getEntry(name: String): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $NAME_COl = $name", null)
    }

    fun getAllEntries(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    companion object {
        private const val DATABASE_NAME = "TASKS_DB"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "TASKS"
        val ID_COL = "id"
        val NAME_COl = "name"
        val DESCRIPTION_COL = "description"
        val DATA_COL = "data"
        val STATUS_COL = "status"
    }
}