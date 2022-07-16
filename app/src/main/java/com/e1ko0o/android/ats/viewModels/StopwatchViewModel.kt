package com.e1ko0o.android.ats.viewModels

import android.app.Application
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import androidx.lifecycle.AndroidViewModel

class StopwatchViewModel(application: Application) : AndroidViewModel(application) {
    private var startTime: Long = 0
    private var pauseTime: Long = 0
    private var base: Long = 0

    var isRunning: Boolean = false

    fun start(chronometer: Chronometer) {
        isRunning = true
        startTime = SystemClock.elapsedRealtime()
        chronometer.base = startTime - pauseTime
        pauseTime = 0
        chronometer.start()
    }

    fun pause(chronometer: Chronometer) {
        isRunning = false
        pauseTime = SystemClock.elapsedRealtime() - chronometer.base
        chronometer.stop()
    }

    fun initialReset(chronometer: Chronometer) {
        startTime = SystemClock.elapsedRealtime()
        // below to running timer
        if (base != 0L && pauseTime == 0L)
            chronometer.base = startTime + pauseTime - (startTime - base)
        // below to paused timer
        else if (base != 0L && pauseTime != 0L)
            chronometer.base = startTime - pauseTime
        // below to initial state (timer is 00:00)
        else
            chronometer.base = base + startTime + pauseTime

        if (isRunning)
            chronometer.start()
        else
            chronometer.stop()
    }

    fun reset(chronometer: Chronometer) {
        isRunning = false
        pauseTime = 0
        base = 0
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.stop()
    }

    fun saveState(base: Long) {
        if (pauseTime == 0L)
            this.base = base
        else
            this.base = pauseTime
    }

    fun getBase() = base
}