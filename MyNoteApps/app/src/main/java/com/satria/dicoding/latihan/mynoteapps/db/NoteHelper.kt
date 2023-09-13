package com.satria.dicoding.latihan.mynoteapps.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.satria.dicoding.latihan.mynoteapps.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.satria.dicoding.latihan.mynoteapps.db.DatabaseContract.NoteColumns.Companion._ID
import java.sql.SQLException
import kotlin.jvm.Throws

class NoteHelper(context: Context) {
    private var dbhelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var db: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME

        private var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context): NoteHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: NoteHelper(context)
        }
    }

    @Throws(SQLException::class)
    fun open() {
        db = dbhelper.writableDatabase
    }

    fun close() {
        dbhelper.close()

        if (db.isOpen) db.close()
    }

    fun queryAll(): Cursor {
        return db.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    fun queryById(id: String): Cursor {
        return db.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?): Long {
        return db.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return db.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteById(id:String):Int{
        return db.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }
}