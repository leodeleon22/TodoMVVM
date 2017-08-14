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

package com.example.android.architecture.blueprints.todoapp.data
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import java.util.UUID

@Entity(tableName = "tasks", primaryKeys = arrayOf("id"))
data class Task
(var title: String,
 var description: String,
 var id: String,
 var isCompleted: Boolean) {
    @Ignore constructor(title: String, description: String ) : this(title, description, UUID.randomUUID().toString(), false)
    @Ignore constructor(title: String, description: String, isCompleted: Boolean ) : this(title, description, UUID.randomUUID().toString(), isCompleted)
    @Ignore constructor(title: String, description: String, id: String? ) : this(title, description, id?: UUID.randomUUID().toString(), false)


    val titleForList: String?
        @Ignore
        get() {
            if (!title.isNullOrEmpty()) {
                return title
            } else {
                return description
            }
        }

    val isActive: Boolean
        @Ignore
        get() = !isCompleted

    val isEmpty: Boolean
        @Ignore
        get() = title.isNullOrEmpty() && description.isNullOrEmpty()

}