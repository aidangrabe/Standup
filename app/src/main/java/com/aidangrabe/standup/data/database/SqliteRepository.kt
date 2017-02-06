package com.aidangrabe.standup.data.database

import java.util.concurrent.Executor

/**
 *
 */
abstract class SqliteRepository(protected val ioExecutor: Executor, protected val callbackExecutor: Executor) {

    protected val database by lazy { StandupDatabase.instance.writableDatabase }

    protected fun doInTransaction(runnable: Runnable) {
        try {
            database.beginTransaction()
            ioExecutor.execute(runnable)
            database.setTransactionSuccessful()
        } finally {
            database.endTransaction()
        }
    }

    protected fun doInTransaction(runnable: () -> Unit) {
        try {
            database.beginTransaction()
            ioExecutor.execute(runnable)
            database.setTransactionSuccessful()
        } finally {
            database.endTransaction()
        }
    }

}