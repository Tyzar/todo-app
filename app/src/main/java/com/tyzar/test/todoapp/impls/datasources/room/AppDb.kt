package com.tyzar.test.todoapp.impls.datasources.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tyzar.test.todoapp.impls.datasources.room.dao.TaskDao
import com.tyzar.test.todoapp.impls.datasources.room.tables.TableTask

@Database(version = 1, entities = [TableTask::class])
abstract class AppDb : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}