package com.yu.pl.app.challeng.notemark.core.ui.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateFormat {

    val yearToSecond:(Locale)-> SimpleDateFormat ={ locale ->
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    }
}