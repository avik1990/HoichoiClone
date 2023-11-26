package com.app.hoichoiclone.utility.customviews.timeago

import android.content.Context
import android.content.res.TypedArray
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import android.text.format.DateUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.app.hoichoiclone.R
import java.lang.ref.WeakReference

class RelativeTimeTextView : AppCompatTextView {
    private var mReferenceTime: Long = 0
    private var mPrefix: String? = null
    private var mSuffix: String? = null
    private val mHandler: Handler = Handler()
    private var mUpdateTimeTask: UpdateTimeRunnable? = null
    private var isUpdateTaskRunning = false

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val a: TypedArray = context.getTheme().obtainStyledAttributes(
            attrs,
            R.styleable.RelativeTimeTextView, 0, 0
        )
        val referenceTimeText: String
        try {
            referenceTimeText = a.getString(R.styleable.RelativeTimeTextView_reference_time).toString()
            mPrefix = a.getString(R.styleable.RelativeTimeTextView_relative_time_prefix)
            mSuffix = a.getString(R.styleable.RelativeTimeTextView_relative_time_suffix)
            mPrefix = if (mPrefix == null) "" else mPrefix
            mSuffix = if (mSuffix == null) "" else mSuffix
        } finally {
            a.recycle()
        }
        mReferenceTime = try {
            java.lang.Long.valueOf(referenceTimeText)
        } catch (nfe: NumberFormatException) {
            /*
                 * TODO: Better exception handling
                 */
            -1L
        }
        setReferenceTime(mReferenceTime)
    }
    /**
     * Returns prefix
     * @return
     */
    /**
     * @param prefix
     *
     * Example:
     * [prefix] in XX minutes
     */
    @get:Deprecated("")
    @set:Deprecated(
        """This method is not suitable for i18n.
      Instead, override {@link #getRelativeTimeDisplayString(long, long)}
      <p/>
      String to be attached before the reference time
      """
    )
    var prefix: String?
        get() = mPrefix
        set(prefix) {
            mPrefix = prefix
            updateTextDisplay()
        }
    /**
     * Returns suffix
     * @return
     */
    /**
     *
     * @param suffix
     *
     * Example:
     * in XX minutes [suffix]
     */
    @get:Deprecated("")
    @set:Deprecated(
        """This method is not suitable for i18n.
      Instead, override {@link #getRelativeTimeDisplayString(long, long)}
      <p/>
      String to be attached after the reference time
      """
    )
    var suffix: String?
        get() = mSuffix
        set(suffix) {
            mSuffix = suffix
            updateTextDisplay()
        }

    /**
     * Sets the reference time for this view. At any moment, the view will render a relative time period relative to the time set here.
     *
     *
     * This value can also be set with the XML attribute `reference_time`
     * @param referenceTime The timestamp (in milliseconds since epoch) that will be the reference point for this view.
     */
    fun setReferenceTime(referenceTime: Long) {
        mReferenceTime = referenceTime

        /*
         * Note that this method could be called when a row in a ListView is recycled.
         * Hence, we need to first stop any currently running schedules (for example from the recycled view.
         */stopTaskForPeriodicallyUpdatingRelativeTime()

        /*
         * Instantiate a new runnable with the new reference time
         */initUpdateTimeTask()

        /*
         * Start a new schedule.
         */startTaskForPeriodicallyUpdatingRelativeTime()

        /*
         * Finally, update the text display.
         */updateTextDisplay()
    }

    private fun updateTextDisplay() {
        /*
         * TODO: Validation, Better handling of negative cases
         */
        if (mReferenceTime == -1L) return
        text = mPrefix + getRelativeTimeDisplayString(
            mReferenceTime,
            System.currentTimeMillis()
        ) + mSuffix
    }

    /**
     * Get the text to display for relative time. By default, this calls [DateUtils.getRelativeTimeSpanString] passing [DateUtils.FORMAT_ABBREV_RELATIVE] flag.
     * <br></br>
     * You can override this method to customize the string returned. For example you could add prefixes or suffixes, or use Spans to style the string etc
     * @param referenceTime The reference time passed in through [.setReferenceTime] or through `reference_time` attribute
     * @param now The current time
     * @return The display text for the relative time
     */
    fun getRelativeTimeDisplayString(referenceTime: Long, now: Long): CharSequence {
        val difference = now - referenceTime
        return if (difference >= 0 && difference <= DateUtils.MINUTE_IN_MILLIS) getResources().getString(
            R.string.just_now
        ) else DateUtils.getRelativeTimeSpanString(
            mReferenceTime,
            now,
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_RELATIVE
        )
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startTaskForPeriodicallyUpdatingRelativeTime()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopTaskForPeriodicallyUpdatingRelativeTime()
    }

    /* fun onVisibilityChanged(changedView: View?, visibility: Int) {
        if (changedView != null) {
            super.onVisibilityChanged(changedView, visibility)
        }
        if (visibility == GONE || visibility == INVISIBLE) {
            stopTaskForPeriodicallyUpdatingRelativeTime()
        } else {
            startTaskForPeriodicallyUpdatingRelativeTime()
        }
    }*/

    private fun startTaskForPeriodicallyUpdatingRelativeTime() {
        if (mUpdateTimeTask!!.isDetached) initUpdateTimeTask()
        mHandler.post(mUpdateTimeTask!!)
        isUpdateTaskRunning = true
    }

    private fun initUpdateTimeTask() {
        mUpdateTimeTask = UpdateTimeRunnable(this, mReferenceTime)
    }

    private fun stopTaskForPeriodicallyUpdatingRelativeTime() {
        if (isUpdateTaskRunning) {
            mUpdateTimeTask!!.detach()
            mHandler.removeCallbacks(mUpdateTimeTask!!)
            isUpdateTaskRunning = false
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState: Parcelable? = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.referenceTime = mReferenceTime
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        val ss = state as SavedState
        mReferenceTime = ss.referenceTime
        super.onRestoreInstanceState(ss.getSuperState())
    }

    class SavedState : BaseSavedState {
        var referenceTime: Long = 0

        constructor(superState: Parcelable?) : super(superState) {}

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeLong(referenceTime)
        }

        private constructor(`in`: Parcel) : super(`in`) {
            referenceTime = `in`.readLong()
        }

        companion object {
            val CREATOR: Parcelable.Creator<SavedState?> = object : Parcelable.Creator<SavedState?> {
                override fun createFromParcel(`in`: Parcel): SavedState? {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }

        override fun describeContents(): Int {
            return 0
        }

        object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }

    private class UpdateTimeRunnable internal constructor(
        rttv: RelativeTimeTextView?,
        private val mRefTime: Long
    ) :
        Runnable {
        private val weakRefRttv: WeakReference<RelativeTimeTextView>

        init {
            weakRefRttv = WeakReference(rttv)
        }

        val isDetached: Boolean
            get() = weakRefRttv.get() == null

        fun detach() {
            weakRefRttv.clear()
        }

        override fun run() {
            val rttv: RelativeTimeTextView = weakRefRttv.get() ?: return
            val difference = Math.abs(System.currentTimeMillis() - mRefTime)
            var interval = INITIAL_UPDATE_INTERVAL
            if (difference > DateUtils.WEEK_IN_MILLIS) {
                interval = DateUtils.WEEK_IN_MILLIS
            } else if (difference > DateUtils.DAY_IN_MILLIS) {
                interval = DateUtils.DAY_IN_MILLIS
            } else if (difference > DateUtils.HOUR_IN_MILLIS) {
                interval = DateUtils.HOUR_IN_MILLIS
            }
            rttv.updateTextDisplay()
            rttv.mHandler.postDelayed(this, interval)
        }
    }

    companion object {
        private const val INITIAL_UPDATE_INTERVAL = DateUtils.MINUTE_IN_MILLIS
    }
}
