package com.e1ko0o.android.ats.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DateFormat
import java.util.*

const val TAG = "e1ko0o.log"

class StopwatchViewModel : ViewModel() {
    val liveData = MutableLiveData<String>() // @todo maybe you should use Date or Time or Timestamp
    private var startTime: Date = Date()
    private var endTime: Date = Date()
    private var pauseTime: Date = Date()

    fun start() {
        val hms = DateFormat.getTimeInstance()
        val date = DateFormat.getDateInstance()
        val fullDateTime = Calendar.getInstance().time
        val millis = Calendar.getInstance().timeInMillis
        Log.d("$TAG hms", hms.format(Date()))
        Log.d("$TAG date", date.format(Date()))
        Log.d("$TAG fullDateTime", fullDateTime.toString())
        Log.d("$TAG millis", millis.toString())
    }

    fun stop() {

    }

    fun pause() {

    }
}