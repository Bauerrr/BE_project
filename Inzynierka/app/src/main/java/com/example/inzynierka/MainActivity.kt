package com.example.inzynierka

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.commit
import com.example.inzynierka.databinding.ActivityMainBinding
import com.example.inzynierka.databinding.ItemGestureRecognizerResultBinding
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener


class MainActivity : AppCompatActivity(), OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener(this)

        if (!hasPermissions(baseContext)) {
            activityResultLauncher.launch(PERMISSIONS_REQUIRED)
        } else {
            binding.bottomNav.selectedItemId = R.id.nav_camera
        }

    }


    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            var permissionGranted = true
            permissions.entries.forEach{
                if (it.key in PERMISSIONS_REQUIRED && it.value == false)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            } else {
                binding.bottomNav.setSelectedItemId(R.id.nav_camera)
            }
        }

    companion object {
        private const val TAG = "Inzynierka"
        private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA, Manifest.permission.ANSWER_PHONE_CALLS)


        fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all{
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.nav_camera -> onCameraClicked()
        R.id.nav_settings -> onSettingsClicked()
        else -> false
    }

    private fun onSettingsClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, SettingsFragment())
        }
        return true
    }

    private fun onCameraClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, CameraFragment())
        }
        return true
    }
}