package com.e1ko0o.android.ats.viewModels

import android.app.Application
import android.os.SystemClock
import android.widget.Chronometer
import androidx.lifecycle.AndroidViewModel

class StopwatchViewModel(application: Application) : AndroidViewModel(application) {
    private var startTime: Long = 0
    private var pauseTime: Long = 0
    private var base: Long = 0

    fun start(chronometer: Chronometer) {
        startTime = SystemClock.elapsedRealtime()
        chronometer.base = base + startTime + pauseTime
        chronometer.start()
    }

    fun pause(chronometer: Chronometer) {
        pauseTime = chronometer.base - SystemClock.elapsedRealtime()
        chronometer.stop()
    }

    fun initialReset(chronometer: Chronometer) {
        startTime = SystemClock.elapsedRealtime()
        pauseTime = 0
        chronometer.base = base + startTime + pauseTime
        chronometer.stop()
    }

    fun reset(chronometer: Chronometer) {
        startTime = SystemClock.elapsedRealtime()
        pauseTime = 0
        base = 0
        chronometer.base = base + startTime + pauseTime
        chronometer.stop()
    }

    fun saveState() {
        this.base = pauseTime
    }

    fun getBase() = base
}