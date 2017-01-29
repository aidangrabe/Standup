package com.aidangrabe.standup.today

import com.aidangrabe.standup.data.database.TodoItemRepository
import com.aidangrabe.standup.list.ListFragment
import com.aidangrabe.standup.rows.LabelRow

/**
 *
 */
class TodayListFragment : ListFragment() {

    override fun onResume() {
        super.onResume()
        getItems()
    }

    private fun getItems() {
        adapter.clearRows()
        TodoItemRepository.getTodayTodoItems {
            it.forEach { item ->
                adapter.addRow(LabelRow(item.title))
            }
            adapter.notifyDataSetChanged()
        }
    }

}