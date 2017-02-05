package com.aidangrabe.standup

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.aidangrabe.standup.createitem.CreateItemActivity
import com.aidangrabe.standup.data.Type
import com.aidangrabe.standup.data.database.TodoItemRepository
import com.roughike.bottombar.BottomBar

class MainActivity : AppCompatActivity() {

    val bottomNav by lazy { findViewById(R.id.bottom_navigation) as BottomBar }
    val viewPager by lazy { findViewById(R.id.view_pager) as ViewPager }
    var type = Type.Today.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById(R.id.fab).setOnClickListener {
            val intent = Intent(this, CreateItemActivity::class.java)
            intent.putExtra("type", type)
            startActivity(intent)
        }

        viewPager.adapter = MainFragmentAdapter(supportFragmentManager)

        bottomNav.setOnTabSelectListener {
            val tabPosition = bottomNav.findPositionForTabWithId(it)
            viewPager.setCurrentItem(tabPosition, true)
        }
        bottomNav.selectTabWithId(R.id.nav_today)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_clear_all -> {
            TodoItemRepository.clearType(type)
            true
        }
        else -> true
    }

}
