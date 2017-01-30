package com.aidangrabe.standup

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.aidangrabe.standup.blockers.BlockersListFragment
import com.aidangrabe.standup.createitem.CreateItemActivity
import com.aidangrabe.standup.data.Type
import com.aidangrabe.standup.today.TodayListFragment
import com.aidangrabe.standup.yesterday.YesterdayListFragment
import com.roughike.bottombar.BottomBar

class MainActivity : AppCompatActivity() {

    val bottomNav by lazy { findViewById(R.id.bottom_navigation) as BottomBar }
    var type = Type.Today.toString()

    companion object {
        val TAG_YESTERDAY_FRAGMENT = "frag_yesterday"
        val TAG_TODAY_FRAGMENT = "frag_today"
        val TAG_BLOCKERS_FRAGMENT = "frag_blocker"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav.setOnTabSelectListener { selectScreen(it) }

        findViewById(R.id.fab).setOnClickListener {
            val intent = Intent(this, CreateItemActivity::class.java)
            intent.putExtra("type", type)
            startActivity(intent)
        }

        bottomNav.selectTabWithId(R.id.nav_today)

    }

    private fun selectScreen(id: Int): Boolean {
        when (id) {
            R.id.nav_yesterday -> {
                type = Type.Yesterday.toString()
                findOrCreateFragmentAndSet(TAG_YESTERDAY_FRAGMENT) {
                    YesterdayListFragment()
                }
            }
            R.id.nav_today -> {
                type = Type.Today.toString()
                findOrCreateFragmentAndSet(TAG_TODAY_FRAGMENT) {
                    TodayListFragment()
                }
            }
            R.id.nav_blockers -> {
                type = Type.Blocker.toString()
                findOrCreateFragmentAndSet(TAG_BLOCKERS_FRAGMENT) {
                    BlockersListFragment()
                }
            }
        }

        return true
    }

    private fun findOrCreateFragmentAndSet(tag: String, factory: () -> Fragment) {
        setFragment(tag, findOrCreateFragment(tag, factory))
    }

    private fun findOrCreateFragment(tag: String, factory: () -> Fragment): Fragment {
        return supportFragmentManager.findFragmentByTag(tag) ?: factory()
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_content_frame, fragment, tag)
                .commit()
    }

}
