package com.aidangrabe.standup.notifications

import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat
import com.aidangrabe.standup.R
import com.aidangrabe.standup.data.TodoItem
import com.aidangrabe.standup.data.database.TodoItemRepository

/**
 *
 */
class NotificationPresenter(val context: Context) {

    val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    companion object {
        val ID_YESTERDAY = 0
        val ID_TODAY = 1
        val ID_BLOCKERS = 2

        fun showStandupNotification(context: Context) {
            TodoItemRepository.getAllTodoItems { yesterday, today, blockers ->

                val notificationPresenter = NotificationPresenter(context)

                notificationPresenter.showYesterdayNotification(yesterday)
                notificationPresenter.showTodayNotification(today)
                notificationPresenter.showBlockersNotification(blockers)

            }
        }

    }

    fun showYesterdayNotification(items: List<TodoItem>) {
        showNotificationFor(ID_YESTERDAY, "Yesterday", items)
    }

    fun showTodayNotification(items: List<TodoItem>) {
        showNotificationFor(ID_TODAY, "Today", items)
    }

    fun showBlockersNotification(items: List<TodoItem>) {
        showNotificationFor(ID_BLOCKERS, "Blockers", items)
    }

    private fun showNotificationFor(id: Int, title: String, todoItems: List<TodoItem>) {
        if (todoItems.isEmpty()) {
            return
        }

        val numItemsText = "${todoItems.size} items"

        val style = NotificationCompat.InboxStyle()
                .setBigContentTitle(title)
                .setSummaryText(numItemsText)

        todoItems.forEach {
            style.addLine(" - ${it.title}")
        }

        val notification = NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(numItemsText)
                .setStyle(style)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build()

        notificationManager.notify(id, notification)
    }

}