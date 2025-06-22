package com.yu.pl.app.challeng.notemark.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteMarkDao {

    @Query("SELECT * FROM notemark")
    fun getAllNoteMark(): Flow<List<NoteMarkEntity>>

    @Insert
    suspend fun insertNoteMark(noteMark: NoteMarkEntity):Long

    @Update
    suspend fun updateNoteMark(noteMark: NoteMarkEntity):Int

    @Delete
    suspend fun deleteNoteMark(noteMark: NoteMarkEntity):Int
}