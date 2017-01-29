package com.aidangrabe.standup.data.database.tables

import android.database.sqlite.SQLiteDatabase

/**
 *
 */
object TodoItemTable : Table {

    val ID = "todo_item_id"
    val TITLE = "todo_item_title"
    val TYPE = "todo_item_type"

    override fun tableName() = "TodoItems"

    override fun createTable(db: SQLiteDatabase) {
        println("createTable()")
        val sql = "CREATE TABLE ${tableName()} (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$TITLE TEXT," +
                "$TYPE TEXT" +
                ");"
        db.execSQL(sql)
    }

    override fun upgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        println("upgrade()")
        db.let {
            drop(it)
            createTable(it)
        }
    }

    override fun downgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        println("downgrade()")
        upgrade(db, oldVersion, newVersion)
    }


}