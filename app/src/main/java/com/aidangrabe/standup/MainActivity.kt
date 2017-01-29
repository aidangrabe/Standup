package com.aidangrabe.standup

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.aidangrabe.standup.blockers.BlockersListFragment
import com.aidangrabe.standup.createitem.CreateItemActivity
import com.aidangrabe.standup.today.TodayListFragment
import com.aidangrabe.standup.yesterday.YesterdayListFragment

class MainActivity : AppCompatActivity() {

    val bottomNav by lazy { findViewById(R.id.bottom_navigation) as BottomNavigationView }

    companion object {
        val TAG_YESTERDAY_FRAGMENT = "frag_yesterday"
        val TAG_TODAY_FRAGMENT = "frag_today"
        val TAG_BLOCKERS_FRAGMENT = "frag_blocker"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav.setOnNavigationItemSelectedListener { selectScreen(it.itemId) }

        findViewById(R.id.fab).setOnClickListener {
            val intent = Intent(this, CreateItemActivity::class.java)
            startActivity(intent)
        }

        selectScreen(R.id.nav_today)

    }

    private fun selectScreen(id: Int): Boolean {
        when (id) {
            R.id.nav_yesterday -> {
                findOrCreateFragmentAndSet(TAG_YESTERDAY_FRAGMENT) {
                println("yesterday!")
                    YesterdayListFragment()
                }
            }
            R.id.nav_today -> {
                findOrCreateFragmentAndSet(TAG_TODAY_FRAGMENT) {
                    println("today!")
                    TodayListFragment()
                }
            }
            R.id.nav_blockers -> {
                findOrCreateFragmentAndSet(TAG_BLOCKERS_FRAGMENT) {
                println("blockers!")
                    BlockersListFragment()
                }
            }
        }
        bottomNav.menu.findItem(id).isChecked = true
        return true
    }

    private fun findOrCreateFragmentAndSet(tag: String, factory: () -> Fragment) {
        setFragment(tag, findOrCreateFragment(tag, factory))
    }

    private fun findOrCreateFragment(tag: String, factory: () -> Fragment): Fragment {
        return supportFragmentManager.findFragmentByTag(tag) ?: factory()
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        println("Set fragment: ${fragment.javaClass.name}")
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_content_frame, fragment, tag)
                .commit()
    }

}
