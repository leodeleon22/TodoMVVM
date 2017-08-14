package com.example.android.architecture.blueprints.todoapp.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.android.architecture.blueprints.todoapp.data.Task

/**
 * Created by leo on 13/08/2017.
 */
@Database(entities = arrayOf(Task::class), version = 1)
abstract class TasksDb : RoomDatabase() {
    abstract fun tasksDao(): TasksDao
}