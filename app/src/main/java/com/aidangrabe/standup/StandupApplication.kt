package com.aidangrabe.standup

import android.app.Application
import com.aidangrabe.standup.data.database.StandupDatabase

/**
 *
 */
class StandupApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        StandupDatabase.init(this)
    }

}