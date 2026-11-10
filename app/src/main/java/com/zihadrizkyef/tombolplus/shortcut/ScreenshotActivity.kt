package com.zihadrizkyef.tombolplus.shortcut

import android.widget.Toast
import com.zihadrizkyef.tombolplus.R
import com.zihadrizkyef.tombolplus.data.Prefs
import com.zihadrizkyef.tombolplus.service.TombolAccessibility

/**
 * Ikon "Screenshot": jadwalkan GLOBAL_ACTION_TAKE_SCREENSHOT setelah jeda
 * (default 5 detik), lalu tutup diri. Jeda itu waktu bagi user untuk membuka
 * aplikasi yang mau ditangkap, jadi yang terambil bukan layar Tombol+.
 */
class ScreenshotActivity : ShortcutActivity() {

    override fun perform(): Boolean {
        if (!accessibilityReady()) return false

        val delaySec = Prefs(this).screenshotDelaySec
        Toast.makeText(
            this,
            getString(R.string.toast_screenshot_wait, delaySec),
            Toast.LENGTH_SHORT
        ).show()

        // Service sedang menyambung ulang: jangan buka panduan, cukup minta ulang.
        if (!TombolAccessibility.scheduleScreenshot(delaySec * 1000L)) toastBusy()
        return true
    }
}
