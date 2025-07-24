package com.yu.pl.app.challeng.notemark.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteMarkDao {

    @Query("SELECT * FROM notemark WHERE isDelete is 0")
    fun getAllNoteMark(): Flow<List<NoteMarkEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNoteMark(noteMark: NoteMarkEntity): Long

    @Update
    suspend fun updateNoteMark(noteMark: NoteMarkEntity): Int

    @Delete
    suspend fun deleteNoteMark(noteMark: NoteMarkEntity): Int

    @Query("Delete From notemark")
    suspend fun deleteAllNoteMark()

    @Query("Select * FROM notemark WHERE id is :id")
    suspend fun getById(id:String): NoteMarkEntity?

    @Transaction
    suspend fun insertOrUpdateWithConflictCheck(newEntity: NoteMarkEntity){
        val existingEntity = getById(newEntity.id)
        if(existingEntity == null){
            insertNoteMark(newEntity)
        }else{
            if(newEntity.lastEditedAt > existingEntity.lastEditedAt){
                updateNoteMark(newEntity)
            }
        }
    }

}