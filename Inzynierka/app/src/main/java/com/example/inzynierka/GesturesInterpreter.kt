package com.example.inzynierka

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Build
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.view.KeyEvent
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.mediapipe.tasks.components.containers.Category

class GesturesInterpreter(private val context: Context) {
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    //private val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    private val telecomManager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
    private var labelsList: MutableList<String> = mutableListOf()
    private var lastExecutionTime = 0L
    // Test what's the good amount of time for a cooldown value
    private val cooldownTime = 2000L

    // Gestures in gesture recognition
    // ["None", "Closed_Fist", "Open_Palm", "Pointing_Up", "Thumb_Down", "Thumb_Up", "Victory", "ILoveYou"]
    // ILoveYou - playSong
    // Thumb_down - skipSong
    // Thumb_up - previousSong
    // Open_palm - stopSong
    // Victory - acceptIncomingCall

    private fun skipSong(){
        val event = KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT)
        audioManager.dispatchMediaKeyEvent(event)
    }

    private fun pauseSong(){
        val event = KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PAUSE)
        audioManager.dispatchMediaKeyEvent(event)
    }

    private fun playSong(){
        val event = KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY)
        audioManager.dispatchMediaKeyEvent(event)
    }

    private fun previousSong(){
        val event = KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PREVIOUS)
        audioManager.dispatchMediaKeyEvent(event)
    }


    private fun acceptIncomingCall(){
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ANSWER_PHONE_CALLS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        telecomManager.acceptRingingCall()
    }


    private fun endIncomingCall(){
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ANSWER_PHONE_CALLS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        telecomManager.endCall()
    }


    fun addLabel(labelCategory: List<Category>?){
        //sprawdź co się dzieje gdy empty tu będzie
        val currentTime = System.currentTimeMillis()

        // Stop executing for a certain amount of time, to prevent spamming audio keys uncontrollably
        if (currentTime - lastExecutionTime >= cooldownTime){
            var label: String? = null
            labelCategory?.get(0)?.let{ category ->
                label = category.categoryName().toString()
            }
            if (labelsList.size <= 20){
                label?.let { labelsList.add(it) }
            } else {
                // Count labels and select most common one
                val labelsCounter = labelsList.groupingBy { it }.eachCount()
                val gestureLabel = labelsCounter.maxBy { it.value }.key
                println(gestureLabel)
                labelsList.clear()

                // Don't put function into sleep if detected gesture is None
                if(gestureLabel != "None"){
                    lastExecutionTime = System.currentTimeMillis()
                }

                when (gestureLabel) {
                    "Closed_Fist" -> playSong()
                    "Open_Palm" -> pauseSong()
                    "Thumb_Down" -> skipSong()
                    "Thumb_Up" -> previousSong()
                    "Victory" -> acceptIncomingCall()
                    "Pointing_Up" -> endIncomingCall()
                }
            }
        }

    }

}