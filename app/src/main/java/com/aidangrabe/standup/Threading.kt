package com.aidangrabe.standup

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 *
 */
object Threading {

    val DB_EXECUTOR = Executors.newSingleThreadExecutor()
    val IO_EXECUTOR = Executors.newFixedThreadPool(2)
    val MAIN_THREAD_EXECUTOR = MainThreadExecutor()

    class MainThreadExecutor : Executor {
        private val handler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            handler.post(command)
        }
    }

}

