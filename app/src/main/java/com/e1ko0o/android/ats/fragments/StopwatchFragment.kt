package com.e1ko0o.android.ats.fragments

import android.os.Bundle
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

        binding.btnStart.setOnClickListener { viewModel.start(binding.chronometer) }
        binding.btnPause.setOnClickListener { viewModel.pause(binding.chronometer) }
        binding.btnReset.setOnClickListener { viewModel.reset(binding.chronometer) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false)
    }

    companion object {
        fun newInstance(): StopwatchFragment {
            return StopwatchFragment()
        }
    }
}