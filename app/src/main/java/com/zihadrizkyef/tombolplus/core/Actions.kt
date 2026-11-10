package com.zihadrizkyef.tombolplus.core

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.media.AudioManager
import android.os.Build
import com.zihadrizkyef.tombolplus.service.TombolAccessibility

/**
 * Empat aksi. Tidak ada root, tidak ada Shizuku, tidak ada overlay: kunci layar,
 * menu daya, dan tangkap layar dijalankan Android sendiri lewat
 * AccessibilityService; volume memakai panel volume bawaan sistem.
 */
object Actions {

    /** Android 9+. */
    fun lock(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) return false
        return TombolAccessibility.global(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN)
    }

    /** Android 5+. Membuka power menu bawaan; user memilih "Mulai ulang" di sana. */
    fun powerDialog(): Boolean =
        TombolAccessibility.global(AccessibilityService.GLOBAL_ACTION_POWER_DIALOG)

    /** Android 11+. Tangkapan layar disimpan Android seperti tombol fisik. */
    fun screenshot(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) return false
        return TombolAccessibility.global(AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT)
    }

    /**
     * Menampilkan panel volume media bawaan tanpa mengubah levelnya: nilai
     * sekarang ditulis ulang dengan FLAG_SHOW_UI. Slider di panel itu bisa
     * langsung digeser seperti menekan tombol volume fisik.
     */
    fun showVolumePanel(ctx: Context) {
        val am = ctx.getSystemService(AudioManager::class.java) ?: return
        val stream = AudioManager.STREAM_MUSIC
        runCatching {
            am.setStreamVolume(stream, am.getStreamVolume(stream), AudioManager.FLAG_SHOW_UI)
        }
    }
}
