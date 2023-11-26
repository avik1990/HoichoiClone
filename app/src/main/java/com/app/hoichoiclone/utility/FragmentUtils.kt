package com.app.hoichoiclone.utility

import androidx.fragment.app.Fragment

object Utils {
    fun Fragment.addChildFragment(fragment: Fragment, frameId: Int) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(frameId, fragment).commit()
    }
}
