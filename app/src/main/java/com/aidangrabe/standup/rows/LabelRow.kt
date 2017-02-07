package com.aidangrabe.standup.rows

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.aidangrabe.standup.R
import com.aidangrabe.standup.list.Row

/**
 *
 */
class LabelRow(val text: String) : Row {

    companion object {
        val COLORS = listOf(
                Color.parseColor("#F44336"),
                Color.parseColor("#E91E63"),
                Color.parseColor("#9C27B0"),
                Color.parseColor("#673AB7"),
                Color.parseColor("#3F51B5"),
                Color.parseColor("#2196F3")
        )
    }

    override fun viewHolderFactory(): (View) -> RecyclerView.ViewHolder {
        return ::ViewHolder
    }

    override fun bind(viewHolder: RecyclerView.ViewHolder) {
        with (viewHolder as ViewHolder) {
            label.text = text
            val firstLetter = Character.toUpperCase(text[0])
            characterLabel.text = firstLetter.toString()
            tintLetterBackground(characterLabel, firstLetter)
        }
    }

    override fun viewId() = R.layout.row_label

    private fun tintLetterBackground(textView: TextView, firstLetter: Char) {
        val background = textView.background
        background?.let {
            if (it is GradientDrawable) {
                val letterNum = firstLetter.toInt()
                it.setColor(COLORS[letterNum % COLORS.size])
            }
        }
    }

    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val label: TextView
        val characterLabel: TextView

        init {
            label = itemView.findViewById(R.id.label) as TextView
            characterLabel = itemView.findViewById(R.id.character_label) as TextView
        }

    }

}