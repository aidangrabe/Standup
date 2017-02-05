package com.aidangrabe.standup.blockers

import com.aidangrabe.standup.TodoListFragment
import com.aidangrabe.standup.data.database.TodoItemRepository
import com.aidangrabe.standup.list.ListFragment
import com.aidangrabe.standup.rows.LabelRow

/**
 *
 */
class BlockersListFragment : ListFragment(), TodoListFragment {

    override fun todoType() = "blocker"

    override fun reload() = getItems()

    override fun onResume() {
        super.onResume()
        getItems()
    }

    private fun getItems() {
        adapter.clearRows()
        TodoItemRepository.getBlockers {
            it.forEach { item ->
                adapter.addRow(LabelRow(item.title))
            }
            adapter.notifyDataSetChanged()
        }
    }

}