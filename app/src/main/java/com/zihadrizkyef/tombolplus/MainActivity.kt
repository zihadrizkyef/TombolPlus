package com.zihadrizkyef.tombolplus

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import com.zihadrizkyef.tombolplus.data.Prefs
import com.zihadrizkyef.tombolplus.service.TombolAccessibility
import com.zihadrizkyef.tombolplus.ui.AppRoot
import com.zihadrizkyef.tombolplus.ui.TombolTheme

/**
 * Layar panduan: status aksesibilitas, jeda tangkap layar, dan tombol uji.
 * Aksi sehari-hari tidak lewat sini — itu tugas keempat ikon shortcut.
 */
class MainActivity : ComponentActivity() {

    private val a11yOk = mutableStateOf(false)
    private val delaySec = mutableStateOf(5)
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        prefs = Prefs(this)
        a11yOk.value = TombolAccessibility.isEnabled(this)
        delaySec.value = prefs.screenshotDelaySec

        setContent {
            TombolTheme {
                AppRoot(
                    a11yOk = a11yOk.value,
                    delaySec = delaySec.value,
                    onOpenA11y = { TombolAccessibility.openSettings(this) },
                    onDelayChange = {
                        delaySec.value = it
                        prefs.screenshotDelaySec = it
                    },
                    onRun = { target -> runCatching { startActivity(Intent(this, target)) } }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        a11yOk.value = TombolAccessibility.isEnabled(this)
    }
}
