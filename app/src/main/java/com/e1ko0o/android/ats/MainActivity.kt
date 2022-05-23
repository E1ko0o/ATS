package com.e1ko0o.android.ats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.e1ko0o.android.ats.fragments.AlarmFragment
import com.e1ko0o.android.ats.fragments.StopwatchFragment
import com.e1ko0o.android.ats.fragments.TimerFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val fragment = StopwatchFragment.newInstance()
//            val fragment = TimerFragment.newInstance()
//            val fragment = AlarmFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}