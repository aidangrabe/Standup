package com.aidangrabe.standup.blockers

import com.aidangrabe.standup.TodoListFragment
import com.aidangrabe.standup.data.database.TodoItemRepository

/**
 *
 */
class BlockersListFragment : TodoListFragment() {

    override fun todoType() = "blocker"

    override fun reload() {
        TodoItemRepository.getBlockers {
            setItems(it)
        }
    }

}