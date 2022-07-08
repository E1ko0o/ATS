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

class TimerFragment : Fragment(R.layout.fragment_timer) {
    private lateinit var binding: FragmentTimerBinding
    private lateinit var viewModel: TimerViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimerBinding.bind(view)
        instance = this
        viewModel = ViewModelProvider(this)[TimerViewModel::class.java]

        with(binding) {
            with(viewModel) {
                btnStart.setOnClickListener { start() }
                btnPause.setOnClickListener { pause() }
                btnReset.setOnClickListener { reset() }
                btnSetTime.setOnClickListener {
                    val minutes = if (etMinutes.text.toString().isEmpty())
                        0
                    else
                        etMinutes.text.toString().toInt()
                    val seconds = if (etSeconds.text.toString().isEmpty())
                        0
                    else
                        etSeconds.text.toString().toInt()

                    setTime(minutes, seconds)
                    tvTimer.text = getFormattedTime(minutes, seconds)
                }

                tvTimer.text = getFormattedTime(0, getBase())
                initialReset()
                liveData.observe(viewLifecycleOwner) {
                    tvTimer.text = it
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

    override fun onDestroyView() {
        viewModel.saveState()
        super.onDestroyView()
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
