package com.e1ko0o.android.ats.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            btnStart.setOnClickListener { viewModel.start() }
            btnPause.setOnClickListener { viewModel.pause() }
            btnReset.setOnClickListener { viewModel.reset() }
            btnSetTime.setOnClickListener {
                val minutes = if (etMinutes.text.toString().isEmpty())
                    0
                else
                    etMinutes.text.toString().toInt()
                val seconds = if (etSeconds.text.toString().isEmpty())
                    0
                else
                    etSeconds.text.toString().toInt()

                viewModel.setTime(minutes, seconds)
                tvTimer.text = viewModel.getFormattedTime(minutes, seconds)
            }

            viewModel.liveData.observe(viewLifecycleOwner) {
                tvTimer.text = it
            }

            viewModel.initialReset()
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