package com.e1ko0o.android.ats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.e1ko0o.android.ats.databinding.ActivityMainBinding
import com.e1ko0o.android.ats.fragments.AlarmFragment
import com.e1ko0o.android.ats.fragments.StopwatchFragment
import com.e1ko0o.android.ats.fragments.TimerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // @todo activity recreated with rotating
        changeFragment(TimerFragment.getInstance()) // @todo fragment alarm should be first

        binding.bottomNavView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.imAlarm -> changeFragment(AlarmFragment.newInstance())
                R.id.imTimer -> changeFragment(TimerFragment.getInstance())
                R.id.imStopwatch -> changeFragment(StopwatchFragment.getInstance())
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}