package com.aidangrabe.standup.data.extensions

import android.content.ContentValues
import com.aidangrabe.standup.data.TodoItem
import com.aidangrabe.standup.data.database.TodoItemRepository
import com.aidangrabe.standup.data.database.tables.TodoItemTable

/**
 *
 */

fun TodoItem.save() {
    TodoItemRepository.saveItem(this)
}

fun TodoItem.toContentValues(): ContentValues {
    with (ContentValues()) {
        put(TodoItemTable.TITLE, title)
        put(TodoItemTable.TYPE, type)
        return this
    }
}

fun TodoItem.delete() {
    TodoItemRepository.deleteItem(this)
}