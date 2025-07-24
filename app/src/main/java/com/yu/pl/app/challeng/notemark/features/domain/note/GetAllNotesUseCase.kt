package com.yu.pl.app.challeng.notemark.features.domain.note

import com.yu.pl.app.challeng.notemark.core.database.NoteMarkDao
import com.yu.pl.app.challeng.notemark.core.database.toNoteMark
import com.yu.pl.app.challeng.notemark.core.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllNotesUseCase(
    private val noteMarkDao: NoteMarkDao
) {
    operator fun invoke(): Flow<List<Note>> {
        return noteMarkDao.getAllNoteMark().map { list ->
            list.map { entity ->
                entity.toNoteMark()
            }
        }
    }
}