package com.zihadrizkyef.tombolplus.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Setelan yang tersisa setelah konsep overlay dibuang: hanya jeda tangkap layar.
 * Default 5 detik — cukup untuk pindah ke aplikasi yang mau ditangkap.
 */
class Prefs(context: Context) {

    private val sp: SharedPreferences =
        context.applicationContext.getSharedPreferences("tombol_prefs", Context.MODE_PRIVATE)

    var screenshotDelaySec: Int
        get() = sp.getInt(KEY_SHOT_DELAY, 5).coerceIn(MIN_DELAY, MAX_DELAY)
        set(value) = sp.edit().putInt(KEY_SHOT_DELAY, value.coerceIn(MIN_DELAY, MAX_DELAY)).apply()

    companion object {
        const val MIN_DELAY = 3
        const val MAX_DELAY = 15
        private const val KEY_SHOT_DELAY = "shot_delay"
    }
}
