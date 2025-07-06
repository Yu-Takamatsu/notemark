package com.yu.pl.app.challeng.notemark.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteMarkDao {

    @Query("SELECT * FROM notemark WHERE isDelete is 0")
    fun getAllNoteMark(): Flow<List<NoteMarkEntity>>

    @Insert
    suspend fun insertNoteMark(noteMark: NoteMarkEntity): Long

    @Query(
        """
        UPDATE notemark
        SET
            syncStatus = CASE
                        WHEN syncStatus = "NOT_POSTED" THEN syncStatus
                        ELSE "NOT_UPDATED"
                    END,
            title = :title,
            content = :content,
            lastEditedAt = :lastUpdate,
            isDelete = :isDelete
        WHERE id = :id 
    """
    )
    suspend fun updateNoteMark(id: String, title: String, content: String, lastUpdate: String, isDelete: Boolean = false):Int

    @Query("UPDATE notemark SET syncStatus = :status WHERE id = :id")
    suspend fun updateSyncStatus(id: String, status: SyncStatus)

    @Delete
    suspend fun deleteNoteMark(noteMark: NoteMarkEntity): Int

    @Query("Delete From notemark")
    suspend fun deleteAllNoteMark()
}