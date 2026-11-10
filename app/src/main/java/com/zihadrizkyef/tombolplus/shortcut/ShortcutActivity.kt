package com.zihadrizkyef.tombolplus.shortcut

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.zihadrizkyef.tombolplus.MainActivity
import com.zihadrizkyef.tombolplus.R
import com.zihadrizkyef.tombolplus.service.TombolAccessibility

/**
 * Dasar keempat ikon. Tidak menggambar apa pun: jalankan aksi lalu tutup diri,
 * jadi yang terlihat di layar hanya UI bawaan Android.
 *
 * [perform] mengembalikan false kalau aksesibilitas belum aktif — dalam hal itu
 * layar panduan dibukakan supaya user tahu apa yang harus dinyalakan.
 */
abstract class ShortcutActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ok = runCatching { perform() }.getOrDefault(false)
        if (!ok) {
            Toast.makeText(this, R.string.toast_need_a11y, Toast.LENGTH_LONG).show()
            runCatching {
                startActivity(
                    Intent(this, MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
            }
        }
        finishAndRemoveTask()
    }

    protected abstract fun perform(): Boolean

    protected fun accessibilityReady(): Boolean = TombolAccessibility.isEnabled(this)

    protected fun toastBusy() {
        Toast.makeText(this, R.string.toast_busy, Toast.LENGTH_SHORT).show()
    }
}
