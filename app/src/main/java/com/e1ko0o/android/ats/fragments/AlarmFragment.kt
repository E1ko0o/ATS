package com.e1ko0o.android.ats.fragments

import androidx.fragment.app.Fragment
import com.e1ko0o.android.ats.R
import com.e1ko0o.android.ats.databinding.FragmentAlarmBinding
import com.e1ko0o.android.ats.viewModels.AlarmViewModel

class AlarmFragment : Fragment(R.layout.fragment_alarm) {
    private lateinit var binding: FragmentAlarmBinding
    private lateinit var viewModel: AlarmViewModel

    companion object {
        fun newInstance(): AlarmFragment{
            return AlarmFragment()
        }
    }
}