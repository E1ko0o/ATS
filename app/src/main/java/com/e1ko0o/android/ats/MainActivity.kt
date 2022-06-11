package com.e1ko0o.android.ats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.e1ko0o.android.ats.databinding.ActivityMainBinding
import com.e1ko0o.android.ats.fragments.AlarmFragment
import com.e1ko0o.android.ats.fragments.StopwatchFragment
import com.e1ko0o.android.ats.fragments.TimerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentFragment = StopwatchFragment.newInstance() // @todo alarm
        changeFragment()

        binding.bottomNavView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.imAlarm -> currentFragment = AlarmFragment.newInstance()
                R.id.imTimer -> currentFragment = TimerFragment.newInstance()
                R.id.imStopwatch -> currentFragment = StopwatchFragment.newInstance()
            }
            changeFragment()
            true
        }
    }

    private fun changeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, currentFragment)
            .addToBackStack(null)
            .commit()
    }

}