package com.e1ko0o.android.ats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import com.e1ko0o.android.ats.databinding.ActivityMainBinding
import com.e1ko0o.android.ats.fragments.StopwatchFragment
import com.e1ko0o.android.ats.fragments.TimerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.imTimer -> changeFragment(TimerFragment.getInstance())
                R.id.imStopwatch -> changeFragment(StopwatchFragment.getInstance())
            }
            true
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        if (binding.fragmentContainer.isEmpty())
            changeFragment(TimerFragment.getInstance())
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
