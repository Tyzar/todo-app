package com.tyzar.test.todoapp.impls.datasources.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tyzar.test.todoapp.impls.datasources.room.tables.TableTask

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(task: TableTask)

    @Update
    fun update(task: TableTask)

    @Query("DELETE FROM task WHERE id = :taskId")
    fun delete(taskId: String)

    @Query("SELECT * FROM task WHERE dateTime >= :startDate AND dateTime <= :endDate ORDER BY dateTime ASC")
    fun get(startDate: String, endDate: String): List<TableTask>

    @Query("SELECT * FROM task WHERE id = :taskId")
    fun get(taskId: String): TableTask?
}