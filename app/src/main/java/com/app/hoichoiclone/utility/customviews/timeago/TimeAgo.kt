package com.app.hoichoiclone.utility.customviews.timeago

object TimeAgo {

    private val SECOND_MILLIS = 1000
    private val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private val DAY_MILLIS = 24 * HOUR_MILLIS
    private val WEEK_MILLIS = 7 * DAY_MILLIS
    private val MONTH_MILLIS = 7 * WEEK_MILLIS
    private val YEAR_MILLIS = 7 * DAY_MILLIS

    fun getTimeAgo(time: Long): String? {
        var time = time
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }

        val diff = now - time
        return if (diff < MINUTE_MILLIS) {
            "just now"
        } else if (diff < 2 * MINUTE_MILLIS) {
            "a minute ago"
        } else if (diff < 50 * MINUTE_MILLIS) {
            (diff / MINUTE_MILLIS).toString() + " minutes ago"
        } else if (diff < 90 * MINUTE_MILLIS) {
            "an hour ago"
        } else if (diff < 24 * HOUR_MILLIS) {
            (diff / HOUR_MILLIS).toString() + " hours ago"
        } else if (diff < 48 * HOUR_MILLIS) {
            "yesterday"
        } else if (diff < 7 * DAY_MILLIS) {
            (diff / DAY_MILLIS).toString() + " days ago"
        } else if (diff < 2 * WEEK_MILLIS) {
            "a week ago"
        } else {
            (diff / WEEK_MILLIS).toString() + " weeks ago"
        }
    }
}
