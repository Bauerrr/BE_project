package com.example.inzynierka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.inzynierka.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _fragmentSettingsBinding: FragmentSettingsBinding? = null
    private val viewModel: MainViewModel by activityViewModels()

    private val fragmentSettingsBinding
        get() = _fragmentSettingsBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentSettingsBinding =
            FragmentSettingsBinding.inflate(inflater, container, false)
        return fragmentSettingsBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialSlidersValues()
        setSlidersListeners()
    }

    private fun setInitialSlidersValues() {
        fragmentSettingsBinding.sliderMinTrackingConfidence.value = viewModel.getMinHandTrackingConfidence()

        fragmentSettingsBinding.sliderMinHandDetectionConfidence.value = viewModel.getMinHandDetectionConfidence()

        fragmentSettingsBinding.sliderMinHandPresenceConfidence.value = viewModel.getMinHandPresenceConfidence()
    }

    private fun setSlidersListeners() {
        fragmentSettingsBinding.sliderMinHandPresenceConfidence.addOnChangeListener { slider, value, fromUser ->
            viewModel.setMinHandPresenceConfidence(value)
        }

        fragmentSettingsBinding.sliderMinHandDetectionConfidence.addOnChangeListener { slider, value, fromUser ->
            viewModel.setMinHandDetectionConfidence(value)
        }

        fragmentSettingsBinding.sliderMinTrackingConfidence.addOnChangeListener { slider, value, fromUser ->
            viewModel.setMinHandTrackingConfidence(value)
        }
    }

}