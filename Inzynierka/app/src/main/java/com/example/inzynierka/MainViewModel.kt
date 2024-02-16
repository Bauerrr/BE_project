package com.example.inzynierka

import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {
    private var _delegate: Int = GestureRecognizerHelper.DELEGATE_CPU

    private var _minHandDetectionConfidence: Float =
        GestureRecognizerHelper.DEFAULT_HAND_DETECTION_CONFIDENCE

    private var _minHandTrackingConfidence: Float = GestureRecognizerHelper
        .DEFAULT_HAND_TRACKING_CONFIDENCE

    private var _minHandPresenceConfidence: Float = GestureRecognizerHelper
        .DEFAULT_HAND_PRESENCE_CONFIDENCE

    val currentDelegate: Int get() = _delegate

    val currentMinHandDetectionConfidence: Float
        get() =
            _minHandDetectionConfidence

    val currentMinHandTrackingConfidence: Float
        get() =
            _minHandTrackingConfidence
    val currentMinHandPresenceConfidence: Float
        get() =
            _minHandPresenceConfidence

    fun setDelegate(delegate: Int) {
        _delegate = delegate
    }

    fun setMinHandDetectionConfidence(confidence: Float) {
        _minHandDetectionConfidence = confidence
    }

    fun setMinHandTrackingConfidence(confidence: Float) {
        _minHandTrackingConfidence = confidence
    }

    fun setMinHandPresenceConfidence(confidence: Float) {
        _minHandPresenceConfidence = confidence
    }

    fun getMinHandDetectionConfidence(): Float {
        return _minHandDetectionConfidence
    }

    fun getMinHandTrackingConfidence(): Float {
        return _minHandTrackingConfidence
    }

    fun getMinHandPresenceConfidence(): Float {
        return _minHandPresenceConfidence
    }
}