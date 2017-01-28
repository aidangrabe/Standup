package com.aidangrabe.standup.rows

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.aidangrabe.standup.R
import com.aidangrabe.standup.list.Row

/**
 *
 */
class LabelRow(val text: String) : Row {

    override fun viewHolderFactory(): (View) -> RecyclerView.ViewHolder {
        return ::ViewHolder
    }

    override fun bind(viewHolder: RecyclerView.ViewHolder) {
        with (viewHolder as ViewHolder) {
            label.text = text
            characterLabel.text = text.substring(0, 1).toUpperCase()
        }
    }

    override fun viewId() = R.layout.row_label

    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val label: TextView
        val characterLabel: TextView

        init {
            label = itemView.findViewById(R.id.label) as TextView
            characterLabel = itemView.findViewById(R.id.character_label) as TextView
        }

    }

}