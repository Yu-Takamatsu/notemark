package com.yu.pl.app.challeng.notemark.core.database.sync

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class NoteSyncQueueDao{

    @Query("SELECT * FROM NoteSyncQueue Where userId =:userId ORDER by timestamp ASC")
    abstract suspend fun getAllQueueByUserId(userId: String):List<NoteSyncQueueEntity>

    @Insert
    protected abstract suspend fun insertQueueEntity(entity: NoteSyncQueueEntity)

    @Transaction
    open suspend fun insertCreateQueue(entity: NoteSyncQueueEntity){
        require(entity.type == QueueType.Created){
            "QueueType must be Created"
        }
        insertQueueEntity(entity)
    }

    @Transaction
    open suspend fun insertDeletedQueue(entity: NoteSyncQueueEntity){
        require(entity.type == QueueType.Deleted){
            "QueueType must be Deleted"
        }

        deleteAllQueuesByNoteIdAndQueueType(entity.noteId, QueueType.Updated)
        val counts = deleteAllQueuesByNoteIdAndQueueType(entity.noteId, QueueType.Created)

        if(counts == 0){
            insertQueueEntity(entity)
        }
    }

    @Transaction
    open suspend fun insertUpdatedQueue(entity: NoteSyncQueueEntity){
        require(entity.type == QueueType.Updated){
            "QueueType must be Updated"
        }
        deleteAllQueuesByNoteIdAndQueueType(entity.noteId, QueueType.Updated)
        insertQueueEntity(entity)
    }

    @Query("DELETE FROM NoteSyncQueue Where noteId =:noteId AND type = :type")
    abstract suspend fun deleteAllQueuesByNoteIdAndQueueType(noteId:String, type: QueueType):Int

    @Query("DELETE FROM NoteSyncQueue Where id =:id")
    abstract suspend fun deleteQueueById(id: Int)

}