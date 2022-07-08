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
        instance = this
        viewModel = ViewModelProvider(this)[StopwatchViewModel::class.java]

        with(binding) {
            with(viewModel) {
                btnStart.setOnClickListener { start(chronometer) }
                btnPause.setOnClickListener { pause(chronometer) }
                btnReset.setOnClickListener { reset(chronometer) }

                chronometer.base = getBase()
                initialReset(chronometer)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false)
    }

    override fun onDestroyView() {
        viewModel.saveState()
        super.onDestroyView()
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
