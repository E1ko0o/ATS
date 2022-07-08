package com.e1ko0o.android.ats.viewModels

import android.app.Application
import android.content.Context.VIBRATOR_SERVICE
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
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
                if (i == 0) {
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
        curTime = 0
        liveData.value = getFormattedTime(base + startTime + pauseTime + curTime)
        job.cancel(
            "Job canceled by TimerViewModel.initialReset()",
            Throwable("Job canceled by TimerViewModel.initialReset()")
        )
    }

    fun saveState() {
        base += curTime + startTime + pauseTime
    }

    fun getBase() = base

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

    fun getFormattedTime(minutes: Int, seconds: Int) = getFormattedTime(minutes * 60 + seconds)

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
