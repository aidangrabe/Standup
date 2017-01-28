package com.aidangrabe.standup.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aidangrabe.standup.rows.LabelRow

/**
 *
 */
class ListFragment : Fragment() {

    lateinit var adapter: RowAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val context = inflater.context
        val recyclerView = RecyclerView(context)
        adapter = RowAdapter(LayoutInflater.from(context))
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)


        adapter.addRow(LabelRow("Hello World!"))
        adapter.addRow(LabelRow("Woop"))
        adapter.addRow(LabelRow("yay"))
        adapter.notifyDataSetChanged()

        return recyclerView
    }

}