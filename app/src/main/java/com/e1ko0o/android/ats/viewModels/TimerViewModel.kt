package com.e1ko0o.android.ats.viewModels

import android.app.Application
import android.content.Context.VIBRATOR_SERVICE
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.e1ko0o.android.ats.R
import kotlinx.coroutines.*

class TimerViewModel(application: Application) : AndroidViewModel(application) {
    private var startTime: Int = 0
    private var pauseTime: Int = 0
    private var base: Int = 0
    private var curTime: Int = 0

    private var job: Job = Job()

    val ldTimer: MutableLiveData<String> = MutableLiveData()
    val ldMinutes = MutableLiveData<Int>()
    val ldSeconds = MutableLiveData<Int>()
    val ldPickersState = MutableLiveData<Boolean>()
    val ldButtonStartState = MutableLiveData<Boolean>()

    fun start() {
        startTime = 0
        val timer = base + startTime + pauseTime

        job = viewModelScope.launch {
            for (i in timer downTo 0 step 1) {
                ensureActive()
                ldPickersState.value = false
                ldButtonStartState.value = false
                if ((i != base && pauseTime == 0) || (i <= pauseTime && pauseTime != 0 && i != 0))
                    delay(1000)
                curTime = i
                if (ldMinutes.value != getMinutes(curTime))
                    ldMinutes.value = getMinutes(curTime)
                ldSeconds.value = getSeconds(curTime)

                if (curTime == 0) {
                    val sp = SoundPool.Builder().build()
                    val context = getApplication<Application>().applicationContext
                    sp.load(context, R.raw.praise_the_lord, 1)
                    sp.setOnLoadCompleteListener { soundPool, soundID, status ->
                        if (status == 0) {
                            soundPool.play(soundID, 1F, 1F, 1, 0, 1F)
                            if (Build.VERSION.SDK_INT >= 26) {
                                (context.getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(
                                    VibrationEffect.createOneShot(
                                        2000,
                                        VibrationEffect.DEFAULT_AMPLITUDE
                                    )
                                )
                            } else {
                                (context.getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(
                                    2000
                                )
                            }
                        }
                    }
                    delay(5000)
                    sp.release()
                }
            }
        }
    }

    fun saveState() {
        if (curTime != 0)
            base = curTime
    }

    fun pause() {
        pauseTime = curTime
        base = 0
        ldPickersState.value = false
        ldButtonStartState.value = true
        job.cancel(
            "Job canceled by TimerViewModel.pause()",
            Throwable("Job canceled by TimerViewModel.pause()")
        )
    }

    fun initialReset() {
        startTime = 0
        pauseTime = 0
        curTime = 0
        if (ldMinutes.value != getMinutes(base + startTime + pauseTime + curTime))
            ldMinutes.value = getMinutes(base + startTime + pauseTime + curTime)
        ldSeconds.value = getSeconds(base + startTime + pauseTime + curTime)
    }

    fun reset() {
        startTime = 0
        pauseTime = 0
        base = 0
        curTime = 0
        ldPickersState.value = true
        ldButtonStartState.value = true
        if (ldMinutes.value != getMinutes(base + startTime + pauseTime + curTime))
            ldMinutes.value = getMinutes(base + startTime + pauseTime + curTime)
        ldSeconds.value = getSeconds(base + startTime + pauseTime + curTime)
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

    private fun getMinutes(totalSeconds: Int): Int = totalSeconds / 60
    private fun getSeconds(totalSeconds: Int): Int = totalSeconds % 60
}
