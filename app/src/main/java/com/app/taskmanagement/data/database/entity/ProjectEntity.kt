package com.app.taskmanagement.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.taskmanagement.domain.model.CategoryType
import java.time.LocalDateTime

@Entity(tableName = "project")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "updated_date")
    val updatedDate: LocalDateTime,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "category_type")
    val categoryType: CategoryType,

    @ColumnInfo(name = "due_date")
    val dueDate: LocalDateTime,

    @ColumnInfo(name = "done")
    val done: Boolean
)
