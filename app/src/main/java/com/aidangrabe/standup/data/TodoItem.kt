package com.aidangrabe.standup.data

import android.support.annotation.StringDef
import com.aidangrabe.standup.data.TodoItem.Companion.BLOCKER
import com.aidangrabe.standup.data.TodoItem.Companion.TODAY
import com.aidangrabe.standup.data.TodoItem.Companion.YESTERDAY

/**
 *
 */
data class TodoItem(
        val id: Long = -1L,
        val title: String,
        @ItemType val type: String
) {

    companion object {
        const val YESTERDAY = "yesterday"
        const val TODAY = "today"
        const val BLOCKER = "blocker"
    }

}

@StringDef(YESTERDAY, TODAY, BLOCKER)
@Retention(AnnotationRetention.SOURCE)
annotation class ItemType
