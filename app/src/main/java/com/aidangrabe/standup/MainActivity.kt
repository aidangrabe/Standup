package com.aidangrabe.standup

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aidangrabe.standup.list.ListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = supportFragmentManager.findFragmentByTag("main")
                ?: ListFragment()

        supportFragmentManager.beginTransaction()
                .replace(R.id.main_content_frame, mainFragment, "main")
                .commit()

    }

}
