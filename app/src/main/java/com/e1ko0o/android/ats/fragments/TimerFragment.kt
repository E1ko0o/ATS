package com.e1ko0o.android.ats.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.e1ko0o.android.ats.R
import com.e1ko0o.android.ats.databinding.FragmentTimerBinding
import com.e1ko0o.android.ats.viewModels.TimerViewModel

const val TAG = "MY_TAG"

class TimerFragment : Fragment(R.layout.fragment_timer) {
    private lateinit var binding: FragmentTimerBinding
    private lateinit var viewModel: TimerViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimerBinding.bind(view)
        viewModel = ViewModelProvider(this)[TimerViewModel::class.java]
        instance = this

        with(binding) {
            btnStart.setOnClickListener { viewModel.start(chronometer) }
            btnPause.setOnClickListener { viewModel.pause(chronometer) }
            btnReset.setOnClickListener { viewModel.reset(chronometer) }
            btnSetTime.setOnClickListener {
                val minute = if (etMinutes.text.toString().isEmpty()) 0
                else etMinutes.text.toString().toInt()
                val second = if (etSeconds.text.toString().isEmpty()) 0
                else etSeconds.text.toString().toInt()
                viewModel.setTime(chronometer, minute, second)
            }

            chronometer.base = viewModel.getBase()
            viewModel.initialReset(chronometer)
            chronometer.setOnChronometerTickListener {
                if (viewModel.isFinished(chronometer)) {
                    Toast.makeText(context, "Timer is done!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    companion object {
        private var instance: TimerFragment? = null

        fun getInstance(): TimerFragment {
            instance?.let {
                return it
            }
            return TimerFragment()
        }
    }
}