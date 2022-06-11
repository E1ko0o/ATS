package com.e1ko0o.android.ats.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.e1ko0o.android.ats.R
import com.e1ko0o.android.ats.databinding.FragmentTimerBinding
import com.e1ko0o.android.ats.viewModels.TimerViewModel

class TimerFragment : Fragment(R.layout.fragment_timer) {
    private lateinit var binding: FragmentTimerBinding
    private lateinit var viewModel: TimerViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    companion object {
        fun newInstance(): TimerFragment {
            return TimerFragment()
        }
    }
}