package com.aidangrabe.standup.data.database.tables

import android.database.sqlite.SQLiteDatabase

/**
 *
 */
interface Table {

    fun tableName(): String

    fun createTable(db: SQLiteDatabase)

    fun upgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)

    fun downgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)

}

// extensions

fun Table.drop(db: SQLiteDatabase) {
    println("DROP TABLE IF EXISTS ${tableName()}")
    db.execSQL("DROP TABLE IF EXISTS ${tableName()}")
}