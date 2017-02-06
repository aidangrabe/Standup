package com.aidangrabe.standup.list

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 *
 */
class RowAdapter(val layoutInflater: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val rows = ArrayList<Row>()
    private val viewHolderFactories = SparseArray<(View) -> RecyclerView.ViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val itemView = layoutInflater.inflate(viewType, parent, false)
        return viewHolderFactories[viewType]!!.invoke(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        rows[position].bind(holder)
    }

    override fun getItemCount() = rows.size

    override fun getItemViewType(position: Int) = rows[position].viewId()

    fun addRow(row: Row) {
        rows.add(row)
        viewHolderFactories.put(row.viewId(), row.viewHolderFactory())
    }

    fun itemAtPosition(position: Int) = rows[position]

    fun removeItem(position: Int) {
        rows.removeAt(position)
    }

    fun clearRows() {
        rows.clear()
    }

}