package com.gigih.android.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, "gigih_database", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE ANDROID_TABLE(id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT)")
        db?.execSQL("INSERT INTO ANDROID_TABLE(NAME) VALUES('Generasi')")
        db?.execSQL("INSERT INTO ANDROID_TABLE(NAME) VALUES('Gigih')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}