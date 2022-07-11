package com.e1ko0o.android.ats.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.e1ko0o.android.ats.R
import com.e1ko0o.android.ats.databinding.FragmentTimerSpinnerBinding
import com.e1ko0o.android.ats.viewModels.TimerViewModel

private const val FRAGMENT = R.layout.fragment_timer_spinner

class TimerFragment : Fragment(FRAGMENT) {
    private lateinit var binding: FragmentTimerSpinnerBinding
    private lateinit var viewModel: TimerViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimerSpinnerBinding.bind(view)
        instance = this
        viewModel = ViewModelProvider(this)[TimerViewModel::class.java]

        with(binding) {
            with(viewModel) {
                btnStart.setOnClickListener { start() }
                btnPause.setOnClickListener { pause() }
                btnReset.setOnClickListener {
                    reset()
                    numberPickerMinutes.value = 0
                    numberPickerSeconds.value = 0
                }

                numberPickerMinutes.apply {
                    minValue = 0
                    maxValue = 59
                }
                numberPickerSeconds.apply {
                    minValue = 0
                    maxValue = 59
                }
                val array = mutableListOf<String>()
                for (i in 0..59) {
                    array.add(i.toString())
                }
                numberPickerMinutes.displayedValues = array.toTypedArray()
                numberPickerSeconds.displayedValues = array.toTypedArray()
                numberPickerMinutes.setOnValueChangedListener { numberPicker, oldVal, newVal ->
                    val seconds = numberPickerSeconds.value
                    setTime(newVal, seconds)
                    tvTimer.text = getFormattedTime(newVal, seconds)
                }
                numberPickerSeconds.setOnValueChangedListener { numberPicker, oldVal, newVal ->
                    val minutes = numberPickerMinutes.value
                    setTime(minutes, newVal)
                    tvTimer.text = getFormattedTime(minutes, newVal)
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
        return inflater.inflate(FRAGMENT, container, false)
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
