/*
 * Created by Ivo Zivkov (izivkov@gmail.com) on 2022-03-30, 12:06 a.m.
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2022-03-20, 7:47 p.m.
 */

package org.avmedia.gShockPhoneSync.ui.alarms

import org.avmedia.gshockapi.Alarm

object AlarmsModel {

    val alarms = ArrayList<Alarm>()

    fun isEmpty(): Boolean {
        return alarms.size == 0
    }

}