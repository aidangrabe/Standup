package com.aidangrabe.standup.yesterday

import com.aidangrabe.standup.TodoListFragment
import com.aidangrabe.standup.data.database.TodoItemRepository

/**
 *
 */
class YesterdayListFragment : TodoListFragment() {

    override fun todoType() = "yesterday"

    override fun reload() {
        TodoItemRepository.getYesterdayTodoItems {
            setItems(it)
        }
    }

}