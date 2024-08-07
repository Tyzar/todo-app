package com.tyzar.test.todoapp.di

import android.content.Context
import androidx.room.Room
import com.tyzar.test.todoapp.datasources.TaskLocalData
import com.tyzar.test.todoapp.domain.repositories.TaskRepository
import com.tyzar.test.todoapp.impls.datasources.room.AppDb
import com.tyzar.test.todoapp.impls.datasources.room.TaskLocalDataImpl
import com.tyzar.test.todoapp.impls.repositories.TaskRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    companion object {
        @Provides
        @Singleton
        fun appDb(@ApplicationContext context: Context): AppDb {
            return Room.databaseBuilder(context, AppDb::class.java, "todo-db").build()
        }

        @Provides
        @Singleton
        fun taskLocalDataImpl(appDb: AppDb): TaskLocalDataImpl {
            return TaskLocalDataImpl(appDb)
        }

        @Provides
        fun taskRepoImpl(taskLocalData: TaskLocalData): TaskRepoImpl {
            return TaskRepoImpl(taskLocalData)
        }
    }

    @Binds
    @Singleton
    abstract fun taskLocalData(taskLocalDataImpl: TaskLocalDataImpl): TaskLocalData

    @Binds
    abstract fun taskRepository(taskRepoImpl: TaskRepoImpl): TaskRepository
}