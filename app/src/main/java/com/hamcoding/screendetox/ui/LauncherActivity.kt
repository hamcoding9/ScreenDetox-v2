package com.hamcoding.screendetox.ui

import android.Manifest
import android.app.AppOpsManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hamcoding.screendetox.databinding.ActivityLauncherBinding
import com.hamcoding.screendetox.ui.signin.SignInActivity

class LauncherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLauncherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (hasUsageStatsPermission()) {
            moveToFirstScreen()
        } else {
            requirePermission()
        }
    }

    private fun requirePermission() {
        binding.btnPermission.setOnClickListener {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
    }

    override fun onResume() {
        super.onResume()
        if (hasUsageStatsPermission()) {
            moveToFirstScreen()
        }
    }

    private fun moveToFirstScreen() {
        val account = Firebase.auth.currentUser
        if (account != null) {
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        finish()
    }

    private fun hasUsageStatsPermission(): Boolean {
        val appOps = getSystemService(APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), packageName
            )
        } else {
            appOps.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), packageName
            )
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }
}