package com.aidangrabe.standup.list

import android.support.annotation.IntegerRes
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 *
 */
interface Row {

    fun viewHolderFactory(): (itemView: View) -> RecyclerView.ViewHolder

    fun bind(viewHolder: RecyclerView.ViewHolder)

    @IntegerRes fun viewId(): Int

}