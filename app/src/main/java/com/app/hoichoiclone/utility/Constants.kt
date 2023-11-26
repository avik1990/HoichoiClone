package com.app.hoichoiclone.utility

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val DATBASE_NAME = "hoishoi_db"
    const val NOTE_DATABASE = "note_database"
    const val BUNDLE_NOTE_ID = "bundle_note_id"

    fun convertLongToTime(time: String): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }
}
