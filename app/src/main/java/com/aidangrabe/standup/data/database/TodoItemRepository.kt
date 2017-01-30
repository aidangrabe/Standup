package com.aidangrabe.standup.data.database

import android.content.ContentValues
import android.database.Cursor
import com.aidangrabe.standup.Threading
import com.aidangrabe.standup.data.TodoItem
import com.aidangrabe.standup.data.Type
import com.aidangrabe.standup.data.database.tables.TodoItemTable
import java.util.*

/**
 *
 */
object TodoItemRepository : SqliteRepository(Threading.DB_EXECUTOR, Threading.MAIN_THREAD_EXECUTOR) {

    fun getYesterdayTodoItems(callback: (List<TodoItem>) -> Unit) {
        query("SELECT * FROM ${TodoItemTable.tableName()} WHERE ${TodoItemTable.TYPE}='yesterday'", callback)
    }

    fun getTodayTodoItems(callback: (List<TodoItem>) -> Unit) {
        query("SELECT * FROM ${TodoItemTable.tableName()} WHERE ${TodoItemTable.TYPE}='today'", callback)
    }

    fun getBlockers(callback: (List<TodoItem>) -> Unit) {
        query("SELECT * FROM ${TodoItemTable.tableName()} WHERE ${TodoItemTable.TYPE}='blocker'", callback)
    }

    fun getAllTodoItems(callback: (List<TodoItem>) -> Unit) {
        query("SELECT * FROM ${TodoItemTable.tableName()}", callback)
    }

    fun  clearType(type: String) {
        ioExecutor.execute {
            database.delete(TodoItemTable.tableName(), "${TodoItemTable.TYPE}=?", arrayOf(type))
        }
    }

    fun fromCursor(cursor: Cursor): TodoItem {
        return TodoItem(
                cursor.getString(cursor.getColumnIndex(TodoItemTable.TITLE)),
                Type.fromString(cursor.getString(cursor.getColumnIndex(TodoItemTable.TYPE)))
        )
    }

    private fun query(query: String, callback: (List<TodoItem>) -> Unit) {
        val cursor = database.rawQuery(query, null)
        val list = ArrayList<TodoItem>()
        while (cursor.moveToNext()) {
            list.add(fromCursor(cursor))
        }
        cursor.close()
        callbackExecutor.execute {
            callback(list)
        }
    }

    // region ToDoItem extensions

    fun TodoItem.save() {
        doInTransaction(Runnable {
            database.insert(TodoItemTable.tableName(), null, toContentValues())
        })
    }

    fun TodoItem.toContentValues(): ContentValues {
        with (ContentValues()) {
            put(TodoItemTable.TITLE, title)
            put(TodoItemTable.TYPE, type.toString())
            return this
        }
    }

    // endregion

}