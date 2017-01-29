package com.aidangrabe.standup.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.aidangrabe.standup.data.database.tables.Table
import com.aidangrabe.standup.data.database.tables.TodoItemTable
import java.util.*

/**
 *
 */
class StandupDatabase(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        val DB_NAME = "Standup.db"
        val DB_VERSION = 1
        lateinit var instance: StandupDatabase

        fun init(context: Context) {
            instance = StandupDatabase(context)
        }
    }

    val tables = ArrayList<Table>()

    init {
        tables.add(TodoItemTable)
    }

    override fun onCreate(db: SQLiteDatabase) {
        println("onCreate()")

        tables.forEach {
            it.createTable(db)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        println("onUpgrade()")
        tables.forEach {
            it.upgrade(db, oldVersion, newVersion)
        }
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        println("onDowngrade()")
        tables.forEach {
            it.downgrade(db, oldVersion, newVersion)
        }
    }

}