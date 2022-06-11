package com.e1ko0o.android.ats.viewModels

import android.app.Application
import android.os.SystemClock
import android.widget.Chronometer
import androidx.lifecycle.AndroidViewModel

class StopwatchViewModel(application: Application) : AndroidViewModel(application) {
    private var startTime: Long = 0
    private var pauseTime: Long = 0

    fun start(chronometer: Chronometer) {
        startTime = SystemClock.elapsedRealtime()
        chronometer.base = SystemClock.elapsedRealtime() + pauseTime
        chronometer.start()
    }


    fun pause(chronometer: Chronometer) {
        pauseTime = chronometer.base - SystemClock.elapsedRealtime()
        chronometer.stop()
    }

    fun reset(chronometer: Chronometer) {
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.stop()
    }
}