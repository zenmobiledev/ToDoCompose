package com.mobbelldev.todocompose.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobbelldev.todocompose.data.dao.ToDoDao
import com.mobbelldev.todocompose.data.model.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}