package com.aidangrabe.standup.yesterday

import com.aidangrabe.standup.TodoListFragment
import com.aidangrabe.standup.data.database.TodoItemRepository
import com.aidangrabe.standup.list.ListFragment
import com.aidangrabe.standup.rows.LabelRow

/**
 *
 */
class YesterdayListFragment : ListFragment(), TodoListFragment {

    override fun todoType() = "yesterday"

    override fun onResume() {
        super.onResume()
        getItems()
    }

    private fun getItems() {
        adapter.clearRows()
        TodoItemRepository.getYesterdayTodoItems {
            it.forEach { item ->
                adapter.addRow(LabelRow(item.title))
            }
            adapter.notifyDataSetChanged()
        }
    }

}