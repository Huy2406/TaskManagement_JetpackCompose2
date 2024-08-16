package com.app.taskmanagement.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.taskmanagement.data.database.entity.ProjectAndTasksQuery
import com.app.taskmanagement.data.database.entity.ProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ProjectEntity)

    @Query("DELETE FROM project WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM project WHERE id = :id")
    fun getProject(id: Int): Flow<ProjectAndTasksQuery>

    @Query(
        """
        SELECT *
        FROM project
        WHERE done = :done
        """
    )
    fun getProjectAndTasksQueries(done: Boolean): Flow<List<ProjectAndTasksQuery>>
}
