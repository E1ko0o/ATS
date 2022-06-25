package com.e1ko0o.android.ats.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.e1ko0o.android.ats.R
import com.e1ko0o.android.ats.databinding.FragmentStopwatchBinding
import com.e1ko0o.android.ats.viewModels.StopwatchViewModel


class StopwatchFragment : Fragment(R.layout.fragment_stopwatch) {
    private lateinit var binding: FragmentStopwatchBinding
    private lateinit var viewModel: StopwatchViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStopwatchBinding.bind(view)
        viewModel = ViewModelProvider(this)[StopwatchViewModel::class.java]
        instance = this

        with(binding) {
            btnStart.setOnClickListener { viewModel.start(chronometer) }
            btnPause.setOnClickListener { viewModel.pause(chronometer) }
            btnReset.setOnClickListener { viewModel.reset(chronometer) }

            chronometer.base = viewModel.getBase()
            viewModel.initialReset(chronometer)
        }
    }

    override fun onDestroyView() {
        viewModel.saveState()
        super.onDestroyView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false)
    }

    companion object {
        private var instance: StopwatchFragment? = null

        fun getInstance(): StopwatchFragment {
            instance?.let {
                return it
            }
            return StopwatchFragment()
        }
    }
}