package com.e1ko0o.android.ats.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

const val TAG = "MY_TAG"

class TimerViewModel(application: Application) : AndroidViewModel(application) {
    private var startTime: Int = 0
    private var pauseTime: Int = 0
    private var base: Int = 0
    private var curTime: Int = 0

    private var job: Job = Job()

    val liveData: MutableLiveData<String> = MutableLiveData()

    fun start() {
        startTime = 0
        val timer = base + startTime + pauseTime
        liveData.value = getFormattedTime(timer)

        job = viewModelScope.launch {
            for (i in timer downTo 0 step 1) {
                ensureActive()
                if (i != base)
                    delay(1000)
                curTime = i
                liveData.value = getFormattedTime(curTime)
                if (i == 0)
                    Log.d(TAG, "!!!!!!!!!!!!!!!!!!!DONE!!!!!!!!!!!!!!!!") // @todo use sound
            }
        }
    }

    fun pause() {
        pauseTime = curTime
        base = 0
        job.cancel(
            "Job canceled by TimerViewModel.pause()",
            Throwable("Job canceled by TimerViewModel.pause()")
        )
    }

    fun initialReset() {
        startTime = 0
        pauseTime = 0
        liveData.value = getFormattedTime(base + startTime + pauseTime)
        job.cancel(
            "Job canceled by TimerViewModel.initialReset()",
            Throwable("Job canceled by TimerViewModel.initialReset()")
        )
    }

    fun reset() {
        startTime = 0
        pauseTime = 0
        base = 0
        liveData.value = getFormattedTime(base + startTime + pauseTime)
        job.cancel(
            "Job canceled by TimerViewModel.reset()",
            Throwable("Job canceled by TimerViewModel.reset()")
        )
    }

    fun setTime(minutes: Int, seconds: Int) {
        startTime = 0
        pauseTime = 0
        base = minutes * 60 + seconds
    }

    fun getFormattedTime(minutes: Int, seconds: Int): String {
        return getFormattedTime(minutes * 60 + seconds)
    }

    private fun getFormattedTime(seconds: Int): String {
        val minute = seconds / 60
        val second = seconds % 60
        var s = ""
        if (minute.toString().length == 1 && second.toString().length == 1)
            s = "0$minute:0$second"
        else if (minute.toString().length == 1 && second.toString().length == 2)
            s = "0$minute:$second"
        else if (minute.toString().length == 2 && second.toString().length == 1)
            s = "$minute:0$second"
        else if (minute.toString().length == 2 && second.toString().length == 2)
            s = "$minute:$second"
        return s
    }
}