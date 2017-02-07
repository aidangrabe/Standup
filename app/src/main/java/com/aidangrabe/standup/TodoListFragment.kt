package com.aidangrabe.standup

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.aidangrabe.standup.data.TodoItem
import com.aidangrabe.standup.data.database.TodoItemRepository
import com.aidangrabe.standup.list.ListFragment
import com.aidangrabe.standup.rows.LabelRow


/**
 *
 */
abstract class TodoListFragment : ListFragment() {

    var todoItems: List<TodoItem> = emptyList()

    abstract fun todoType(): String

    abstract fun reload()

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSwipeToDelete()
    }

    private fun setupSwipeToDelete() {
        val callback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = recyclerView.getChildAdapterPosition(viewHolder.itemView)

                adapter.removeItem(position)
                adapter.notifyItemRemoved(position)

                onItemRemoved(position)
            }

        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onResume() {
        super.onResume()
        reload()
    }

    fun setItems(items: List<TodoItem>) {
        adapter.clearRows()

        todoItems = items
        todoItems.forEach { item ->
            adapter.addRow(LabelRow(item.title))
        }
        adapter.notifyDataSetChanged()
    }

    open fun onItemRemoved(position: Int) {
        TodoItemRepository.removeItem(todoItems[position])
    }

}