package com.aidangrabe.standup.data

/**
 *
 */
data class TodoItem(
        val id: Long = -1L,
        val title: String,
        val type: Type
)

enum class Type {
    Today {
        override fun toString() = "today"
    },
    Yesterday {
        override fun toString() = "yesterday"
    },
    Blocker {
        override fun toString() = "blocker"
    };

    companion object {
        fun fromString(type: String) = when (type) {
            "today" -> Today
            "yesterday" -> Yesterday
            "blocker" -> Blocker
            else -> Today
        }
    }
}