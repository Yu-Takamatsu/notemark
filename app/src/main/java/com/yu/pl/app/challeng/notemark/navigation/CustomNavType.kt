package com.yu.pl.app.challeng.notemark.navigation

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteMarkUi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {
    val noteMarkType = object : NavType<NoteMarkUi>(
        isNullableAllowed = false
    ) {
        override fun put(
            bundle: SavedState,
            key: String,
            value: NoteMarkUi,
        ) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun get(
            bundle: SavedState,
            key: String,
        ): NoteMarkUi? {
            return Json.decodeFromString(bundle.getString(key)?: return null)
        }

        override fun serializeAsValue(value: NoteMarkUi): String {
            return Json.encodeToString(value)
        }

        override fun parseValue(value: String): NoteMarkUi {
            return Json.decodeFromString(value)
        }

    }
}