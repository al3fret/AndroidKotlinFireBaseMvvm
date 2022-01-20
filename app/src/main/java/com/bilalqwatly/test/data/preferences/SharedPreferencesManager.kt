package com.bilalqwatly.test.data.preferences

import android.content.Context
import javax.inject.Inject
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.SharedPreferences
import com.bilalqwatly.test.data.model.MeetingRoomModel
import com.bilalqwatly.test.data.model.PeriodModel
import com.bilalqwatly.test.data.model.WorkDayModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList


class SharedPreferencesManager @Inject constructor(
    @param:ApplicationContext
    var _context: Context
) {
    // Shared Preferences
    private val mPreferences: SharedPreferences
    private val mEditor: SharedPreferences.Editor

    // Shared pref mode
    private var PRIVATE_MODE = 0


    fun getMeetingRoomsList(): ArrayList<MeetingRoomModel> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<MeetingRoomModel>>() {}.type
        return gson.fromJson<ArrayList<MeetingRoomModel>>(
            mPreferences.getString(
                PREF_MEETING_ROOMS,
                ""
            ), type
        )
            ?: return ArrayList<MeetingRoomModel>()
    }

    fun getWorkDaysFromNameMeetingRoomList(name: String): List<WorkDayModel>? {

        val all = getMeetingRoomsList()

        for (meetingRoom in all) {
            if (meetingRoom.content.equals(name)) {
                return meetingRoom.workDayModels
            }
        }
        return null
    }

    fun getPeriodsList(nameMeetingRoom: String, dateWorkDay: String): List<PeriodModel>? {

        val all = getMeetingRoomsList()

        for (meetingRoom in all) {
            if (meetingRoom.content.equals(nameMeetingRoom)) {
                for (workDay in meetingRoom.workDayModels!!) {
                    if (workDay.content.equals(dateWorkDay)) {

                        return workDay.periodModels
                    }
                }

            }
        }
        return null
    }

    fun setMeetingRoomsList(list: List<MeetingRoomModel>) {
        val gson = Gson()
        val json = gson.toJson(list)
        mEditor.putString(PREF_MEETING_ROOMS, json)
        mEditor.commit()
    }

    var mobileNumber: String?
        get() = mPreferences.getString(PREF_KEY_MOBILE_NUMBER, "")
        set(mobileNumber) {
            mEditor.putString(PREF_KEY_MOBILE_NUMBER, mobileNumber!!)
            mEditor.commit()
        }

    var isoCode: String?
        get() = mPreferences.getString(PREF_KEY_ISO_CODE, "")
        set(isoCode) {
            mEditor.putString(PREF_KEY_ISO_CODE, isoCode!!)
            mEditor.commit()
        }


    var isAuthenticated: Boolean
        get() = mPreferences.getBoolean(PREF_KEY_IS_AUTHENTICATED, false)
        set(isAuthenticated) {
            mEditor.putBoolean(PREF_KEY_IS_AUTHENTICATED, isAuthenticated)
            mEditor.apply()
        }

    fun clear() {
        mobileNumber = ""
        isoCode = ""
        isAuthenticated = false
    }

    companion object {
        // Shared preferences file name
        private const val PREF_NAME = "com.bilalqwatly.test.pref_name"

        private const val PREF_KEY_MOBILE_NUMBER = "com.bilalqwatly.test.mobile_number"
        private const val PREF_KEY_ISO_CODE = "com.bilalqwatly.test.iso_code"
        private const val PREF_KEY_IS_AUTHENTICATED = "com.bilalqwatly.test.is_authenticated"
        private const val PREF_MEETING_ROOMS = "com.bilalqwatly.test.meeting.rooms"


        fun clearPreferences(context: Context) {
            val prefs = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE
            )
            prefs.edit().clear().apply()
        }

    }

    init {
        mPreferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        mEditor = mPreferences.edit()
    }
}