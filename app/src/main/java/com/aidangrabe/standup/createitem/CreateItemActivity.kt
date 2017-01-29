package com.aidangrabe.standup.createitem

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import com.aidangrabe.standup.R
import com.aidangrabe.standup.data.TodoItem
import com.aidangrabe.standup.data.Type
import com.aidangrabe.standup.data.database.TodoItemRepository.save

/**
 *
 */
class CreateItemActivity : AppCompatActivity() {

    val titleField by lazy { findViewById(R.id.title_field) as EditText }
    var type = Type.Yesterday

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_item)

        val typeString = intent?.extras?.getString("type") ?: Type.Yesterday.toString()
        type = Type.fromString(typeString)

        findViewById(R.id.fab).setOnClickListener { saveButtonClicked(it) }
    }

    fun saveButtonClicked(view: View) {
        val title = titleField.text.toString()
        if (validateInput(title)) {
            saveTodoItem(title)
            finish()
        }
    }

    private fun saveTodoItem(title: String) {
        // todo
        val todoItem = TodoItem(title, type)
        todoItem.save()
    }

    private fun validateInput(title: String): Boolean {
        return title.trim().length > 0
    }

}