package com.app.hoichoiclone.utility.customviews.tabs

import android.content.Context
import com.app.hoichoiclone.R

class NavigationOption(val id: Int, val text: String) {

    companion object {
        fun initNavigationOptions(context: Context, page: String): List<NavigationOption> {
            val temp = arrayListOf<NavigationOption>()
            temp.clear()
            if (page.equals("Home")) {
                temp.add(NavigationOption(id = 1, text = context.resources.getString(R.string.all)))
                temp.add(NavigationOption(id = 2, text = context.resources.getString(R.string.movies)))
                temp.add(NavigationOption(id = 3, text = context.resources.getString(R.string.shows)))
                temp.add(NavigationOption(id = 4, text = context.resources.getString(R.string.watchlist)))
            }

            return temp
        }
    }
}
