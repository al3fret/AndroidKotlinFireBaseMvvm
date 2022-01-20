package com.bilalqwatly.test.data.repository.datasourcelmpl

import android.util.Log
import com.bilalqwatly.test.data.model.AppointmentModel
import com.bilalqwatly.test.data.model.StateModel
import com.bilalqwatly.test.data.preferences.SharedPreferencesManager
import com.bilalqwatly.test.data.repository.datasource.LocalDataSource
import com.bilalqwatly.test.data.repository.datasource.RemoteDataSource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.bilalqwatly.test.data.model.MeetingRoomModel as MeetingRoomModel
import com.google.firebase.database.DatabaseReference


// Class defining the functionality to the methods described in the [RemoteDataSource.kt] interface.
class RemoteDataSourceImpl(
    private val session: SharedPreferencesManager,
    private val localDataSource: LocalDataSource
) : RemoteDataSource {

    companion object {
        private const val KEY_MEETING_ROOMS = "MEETING_ROOMS"
        private const val KEY_BOOKING = "KEY_BOOKING"
        private const val FIREBASE_DATABASE_URL =
            "https://lootahgas-5fd7b-default-rtdb.firebaseio.com" //production

    }

    override fun saveMeetingRoomToFirebase(meetingRoomModel: MeetingRoomModel) {

        val firebaseReference =
            FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL).reference
        firebaseReference.child(KEY_MEETING_ROOMS).push()
            .setValue(meetingRoomModel)
    }


    override fun fetchMeetingRoomsFromFirebase() {

        val firebaseReference =
            FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL).reference
        val databaseReference = firebaseReference.child(KEY_MEETING_ROOMS)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {

                    var meetingRoomModels = ArrayList<MeetingRoomModel>()

                    for (snapshot in dataSnapshot.children) {


                        val curMeetingRoomModel: MeetingRoomModel? =
                            snapshot.getValue(MeetingRoomModel::class.java)
                        meetingRoomModels.add(curMeetingRoomModel!!)
                    }

                    session.setMeetingRoomsList(meetingRoomModels)

                    CoroutineScope(Dispatchers.IO).launch {
//                            recordLocalDataSource.updateRecordStatusToDB(
//                                curState!!.status!!,
//                                curState.id!!.toInt()
//                            )
                    }
                }
            }


            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }


    override fun bookAppointmentToFirebase(appointmentModel: AppointmentModel) {


        val firebaseReference =
            FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL).reference
        firebaseReference.child(KEY_BOOKING).push()
            .setValue(appointmentModel)


        val rootRef = FirebaseDatabase.getInstance().reference.child(KEY_MEETING_ROOMS)

        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var workDayPosition = -1
                var periodPosition = -1

                for (snapshot in dataSnapshot.children) {
                    val meetingRoomModel: MeetingRoomModel? =
                        snapshot.getValue(MeetingRoomModel::class.java)

                    val key = snapshot.key.toString()
                    if (meetingRoomModel!!.name.equals(appointmentModel.meetingRoom)) {

                        for (workDay in meetingRoomModel.workDayModels!!) {
                            workDayPosition++
                            if (workDay.date.equals(appointmentModel.workDay)) {
                                for (period in workDay.periodModels!!) {
                                    periodPosition++
                                    if (period.content == appointmentModel.period) {

                                        rootRef
                                            .child(key)
                                            .child("workDayModels")
                                            .child(workDayPosition.toString())
                                            .child("periodModels")
                                            .child(periodPosition.toString())
                                            .child("available")
                                            .setValue("false");
                                        rootRef
                                            .child(key)
                                            .child("workDayModels")
                                            .child(workDayPosition.toString())
                                            .child("periodModels")
                                            .child(periodPosition.toString())
                                            .child("enable")
                                            .setValue(false);
                                    }
                                }


                            }

                        }
                    }


                }


            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        rootRef.addListenerForSingleValueEvent(eventListener)

    }


}
