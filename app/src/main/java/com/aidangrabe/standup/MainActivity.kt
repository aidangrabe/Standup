package com.aidangrabe.standup

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.aidangrabe.standup.createitem.CreateItemActivity
import com.aidangrabe.standup.data.Type
import com.aidangrabe.standup.data.database.TodoItemRepository
import com.aidangrabe.standup.notifications.NotificationPresenter
import com.roughike.bottombar.BottomBar


class MainActivity : AppCompatActivity() {

    val rootView by lazy { findViewById(R.id.activity_main) as ViewGroup }
    val bottomNav by lazy { findViewById(R.id.bottom_navigation) as BottomBar }
    val viewPager by lazy { findViewById(R.id.view_pager) as ViewPager }
    val adapter by lazy { MainFragmentAdapter(supportFragmentManager) }

    var type = Type.Today.toString()
    var currentBackgroundColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()

        findViewById(R.id.fab).setOnClickListener {
            val intent = Intent(this, CreateItemActivity::class.java)
            intent.putExtra("type", type)
            startActivity(intent)
        }

        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(OnFragmentChangedListener(viewPager, adapter) {
            if (it is TodoListFragment) {
                type = it.todoType()
            }
        })

        bottomNav.setOnTabSelectListener {
            val tabPosition = bottomNav.findPositionForTabWithId(it)
            viewPager.setCurrentItem(tabPosition, true)

            val color = when (tabPosition) {
                1 -> R.color.todayColor
                2 -> R.color.blockerColor
                else -> R.color.yesterdayColor
            }

            if (currentBackgroundColor != 0) {
                val colorFade = ObjectAnimator.ofObject(rootView, "backgroundColor", ArgbEvaluator(),
                        ContextCompat.getColor(this, currentBackgroundColor), ContextCompat.getColor(this, color))
                colorFade.duration = 300
                colorFade.start()
            }

            currentBackgroundColor = color

        }
        bottomNav.selectTabWithId(R.id.nav_today)

    }

    private fun showNotification() {
        NotificationPresenter.showStandupNotification(this)
    }

    private fun setupToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        toolbar?.let {
            setSupportActionBar(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_clear_all -> {
            clearTodosForType(type)
            true
        }
        R.id.action_show_notification -> {
            showNotification()
            true
        }
        else -> false
    }

    private fun clearTodosForType(type: String) {
        TodoItemRepository.clearType(type) {
            val fragment = viewPager.getCurrentFragment() as TodoListFragment
            fragment.reload()
        }
    }

}

fun ViewPager.getCurrentFragment(): Fragment {
    val fragmentPagerAdapter = adapter as FragmentPagerAdapter
    return fragmentPagerAdapter.getItem(currentItem)
}
