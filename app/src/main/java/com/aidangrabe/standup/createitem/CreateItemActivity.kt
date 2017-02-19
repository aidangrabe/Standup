package com.aidangrabe.standup.createitem

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.aidangrabe.standup.R
import com.aidangrabe.standup.data.ItemType
import com.aidangrabe.standup.data.TodoItem
import com.aidangrabe.standup.data.TodoItem.Companion.BLOCKER
import com.aidangrabe.standup.data.TodoItem.Companion.TODAY
import com.aidangrabe.standup.data.TodoItem.Companion.YESTERDAY
import com.aidangrabe.standup.data.extensions.save

/**
 *
 */
class CreateItemActivity : AppCompatActivity() {

    val titleLabel by lazy { findViewById(R.id.title) as TextView }
    val titleField by lazy { findViewById(R.id.title_field) as EditText }

    @ItemType var type: String = TODAY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_item)

        type = intent?.extras?.getString("type") ?: TODAY

        val toolbar = findViewById(R.id.toolbar) as Toolbar?

        toolbar?.let {
            setSupportActionBar(it)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val appBar = findViewById(R.id.app_bar)

        title = ""

        val color = when (type) {
            TODAY -> R.color.todayColor
            BLOCKER -> R.color.blockerColor
            else -> R.color.yesterdayColor
        }
        appBar.setBackgroundColor(ContextCompat.getColor(this, color))

        titleLabel.text = when (type) {
            YESTERDAY -> "New Item For Yesterday"
            TODAY -> "New Item For Today"
            else -> "New Blocker"
        }

        findViewById(R.id.fab).setOnClickListener { saveButtonClicked(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> false
    }

    fun saveButtonClicked(view: View) {
        val title = titleField.text.toString()
        if (validateInput(title)) {
            saveTodoItem(title)
            finish()
        }
    }

    private fun saveTodoItem(@ItemType title: String) {
        val todoItem = TodoItem(title = title, type = type)
        todoItem.save()
    }

    private fun validateInput(title: String): Boolean {
        return title.trim().isNotEmpty()
    }

}