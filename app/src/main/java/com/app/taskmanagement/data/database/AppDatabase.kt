package com.app.taskmanagement.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.taskmanagement.data.database.converter.LocalDateTimeConverter
import com.app.taskmanagement.data.database.dao.ProjectDao
import com.app.taskmanagement.data.database.dao.TaskDao
import com.app.taskmanagement.data.database.entity.ProjectEntity
import com.app.taskmanagement.data.database.entity.TaskEntity

@Database(
    entities = [
        ProjectEntity::class,
        TaskEntity::class
    ],
    version = 1
)
@TypeConverters(
    value = [
        LocalDateTimeConverter::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao
}
