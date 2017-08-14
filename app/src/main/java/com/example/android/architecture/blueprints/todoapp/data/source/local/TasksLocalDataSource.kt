/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp.data.source.local

import android.arch.persistence.room.Room
import android.content.Context
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import com.google.common.base.Preconditions.checkNotNull


/**
 * Concrete implementation of a data source as a db.
 */
class TasksLocalDataSource// Prevent direct instantiation.
private constructor(context: Context) : TasksDataSource {

    private val mTasksDao: TasksDao

    init {
        checkNotNull(context)
        val db = Room.databaseBuilder(context, TasksDb::class.java, "tasks.db").allowMainThreadQueries().build()
        mTasksDao = db.tasksDao()
    }

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        val tasks = mTasksDao.getAllTasks()

        if (tasks.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable()
        } else {
            callback.onTasksLoaded(tasks)
        }

    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {

        val task: Task? = mTasksDao.getTask(taskId)

        task?: callback.onDataNotAvailable()

        callback.onTaskLoaded(task!!)
    }

    override fun saveTask(task: Task) {
        checkNotNull(task)
        mTasksDao.saveTask(task)
    }

    override fun completeTask(task: Task) {
        task.isCompleted = true;
        mTasksDao.updateTask(task)
    }

    override fun completeTask(taskId: String) {
        // Not required for the local data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    override fun activateTask(task: Task) {

    }

    override fun activateTask(taskId: String) {
        // Not required for the local data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    override fun clearCompletedTasks() {
        val tasks = mTasksDao.getCompletedTasks()
        mTasksDao.deleteTasks(tasks)
    }

    override fun refreshTasks() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    override fun deleteAllTasks() {
        mTasksDao.deleteAllTasks()
    }

    override fun deleteTask(taskId: String) {
        mTasksDao.deleteTask(taskId)
    }

    companion object {

        private var INSTANCE: TasksLocalDataSource? = null

        fun getInstance(context: Context): TasksLocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = TasksLocalDataSource(context)
            }
            return INSTANCE as TasksLocalDataSource
        }
    }
}
