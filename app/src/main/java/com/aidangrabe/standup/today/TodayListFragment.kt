package com.aidangrabe.standup.today

import com.aidangrabe.standup.TodoListFragment
import com.aidangrabe.standup.data.database.TodoItemRepository

/**
 *
 */
class TodayListFragment : TodoListFragment() {

    override fun todoType() = "today"

    override fun reload() {
        TodoItemRepository.getTodayTodoItems {
            setItems(it)
        }
    }

}