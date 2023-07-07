package com.gigih.android.utils

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class DatabaseProvider: ContentProvider() {

    companion object {
        const val PROVIDER_NAME = "com.gigih.android/DatabaseProvider"
        const val URL = "content://$PROVIDER_NAME/ANDROID_TABLE"
        val CONTENT_URI = Uri.parse(URL)

        val id = "id"
        val NAME = "NAME"
    }

    private var db: SQLiteDatabase? = null

    override fun onCreate(): Boolean {
        val helper = context?.let { DatabaseHelper(it) }
        db = helper?.writableDatabase
        return db != null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return db?.query("ANDROID_TABLE", projection, selection, selectionArgs, null, null, sortOrder)
    }

    override fun getType(uri: Uri): String {
        return "com.android.gigih.cursor.dir/com.example.androidtable"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        db?.insert("ANDROID_TABLE", null, values)
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val count = db?.delete("ANDROID_TABLE", selection, selectionArgs)
        context?.contentResolver?.notifyChange(uri, null)
        return count ?: 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val count = db?.update("ANDROID_TABLE", values, selection, selectionArgs)
        context?.contentResolver?.notifyChange(uri, null)
        return count ?: 0
    }
}