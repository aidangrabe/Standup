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

    fun getAllTodoItems(callback: (List<TodoItem>, List<TodoItem>, List<TodoItem>) -> Unit) {
        query("SELECT * FROM ${TodoItemTable.tableName()} ORDER BY ${TodoItemTable.TYPE}") {
            val resultMap = it.groupBy { it.type.name }
            println(resultMap)
            val emptyList = Collections.emptyList<TodoItem>()
            callback(resultMap["Yesterday"] ?: emptyList,
                    resultMap["Today"] ?: emptyList,
                    resultMap["Blockers"] ?: emptyList
            )
        }
    }

    fun  clearType(type: String, callback: () -> Unit) {
        ioExecutor.execute {
            database.delete(TodoItemTable.tableName(), "${TodoItemTable.TYPE}=?", arrayOf(type))
            callbackExecutor.execute {
                callback()
            }
        }
    }

    fun fromCursor(cursor: Cursor): TodoItem {
        return TodoItem(
                cursor.getString(cursor.getColumnIndex(TodoItemTable.TITLE)),
                Type.fromString(cursor.getString(cursor.getColumnIndex(TodoItemTable.TYPE)))
        )
    }

    private fun syncQuery(query: String): List<TodoItem> {
        val cursor = database.rawQuery(query, null)
        val list = ArrayList<TodoItem>()
        while (cursor.moveToNext()) {
            list.add(fromCursor(cursor))
        }
        cursor.close()
        return list
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
        doInTransaction {
            database.insert(TodoItemTable.tableName(), null, toContentValues())
        }
    }

    fun TodoItem.toContentValues(): ContentValues {
        with (ContentValues()) {
            put(TodoItemTable.TITLE, title)
            put(TodoItemTable.TYPE, type.toString())
            return this
        }
    }

    fun removeItem(todoItem: TodoItem) {
        doInTransaction {
            val where = "${TodoItemTable.TITLE}=? AND ${TodoItemTable.TYPE}=?"
            val args = arrayOf(todoItem.title, todoItem.type.toString())
            database.delete(TodoItemTable.tableName(), where, args)
        }
    }

    // endregion

}