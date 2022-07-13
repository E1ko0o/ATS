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
                btnStart.setOnClickListener {
                    if (!(numberPickerMinutes.value == 0 && numberPickerSeconds.value == 0))
                        start()
                }
                btnPause.setOnClickListener {
                    pause()
                }
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
                }
                numberPickerSeconds.setOnValueChangedListener { numberPicker, oldVal, newVal ->
                    val minutes = numberPickerMinutes.value
                    setTime(minutes, newVal)
                }

                initialReset()

                ldTimer.observe(viewLifecycleOwner) {
                    tvTimer.text = it
                }
                ldMinutes.observe(viewLifecycleOwner) {
                    numberPickerMinutes.value = it
                }
                ldSeconds.observe(viewLifecycleOwner) {
                    numberPickerSeconds.value = it
                    if (it == 0 && ldMinutes.value == 0) {
                        btnStart.isEnabled = true
                    }
                }
                ldPickersState.observe(viewLifecycleOwner) {
                    numberPickerMinutes.isEnabled = it
                    numberPickerSeconds.isEnabled = it
                }
                ldButtonStartState.observe(viewLifecycleOwner) {
                    btnStart.isEnabled = it
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
