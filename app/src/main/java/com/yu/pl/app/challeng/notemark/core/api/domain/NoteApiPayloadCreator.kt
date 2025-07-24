package com.yu.pl.app.challeng.notemark.core.api.domain

import com.yu.pl.app.challeng.notemark.core.model.Note

interface NoteApiPayloadCreator {
    fun createPayLoad(note: Note):String
}