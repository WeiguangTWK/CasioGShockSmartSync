/*
 * Created by Ivo Zivkov (izivkov@gmail.com) on 2022-03-30, 12:06 a.m.
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2022-03-29, 6:12 p.m.
 */

package org.avmedia.gShockPhoneSync.ui.time

import android.content.Context
import android.util.AttributeSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.avmedia.gShockPhoneSync.MainActivity.Companion.api
import org.avmedia.gShockPhoneSync.ui.time.TimeFragment.Companion.getFragmentScope
import timber.log.Timber

class WatchName @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : com.google.android.material.textview.MaterialTextView(context, attrs, defStyleAttr) {

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (api().isConnected() && api().isNormalButtonPressed()) {
            getFragmentScope().launch(Dispatchers.IO) {
                val name = api().getWatchName()
                Timber.i(">>> setting watch name to $name")
                text = name
            }
        }
    }
}
