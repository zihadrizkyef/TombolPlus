package com.zihadrizkyef.tombolplus.service

import android.accessibilityservice.AccessibilityService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.accessibility.AccessibilityEvent

/**
 * Layanan tanpa konten: tidak membaca layar, tidak menangkap ketikan.
 * Dipakai hanya sebagai pemegang izin untuk performGlobalAction() dan sebagai
 * timer untuk tangkap layar tertunda.
 */
class TombolAccessibility : AccessibilityService() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) = Unit

    override fun onInterrupt() = Unit

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        if (instance === this) instance = null
        super.onDestroy()
    }

    companion object {
        @Volatile
        var instance: TombolAccessibility? = null
            private set

        fun global(action: Int): Boolean = instance?.performGlobalAction(action) ?: false

        /**
         * Tangkap layar setelah [delayMs]. Timer hidup di service, bukan di
         * activity shortcut — activity-nya sudah ditutup supaya yang tertangkap
         * layar aplikasi tujuan, bukan layar kita.
         */
        fun scheduleScreenshot(delayMs: Long): Boolean {
            val service = instance ?: return false
            service.handler.removeCallbacksAndMessages(null)
            service.handler.postDelayed(
                { global(AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT) },
                delayMs.coerceAtLeast(0L)
            )
            return true
        }

        /**
         * Cek lewat Settings.Secure, bukan lewat [instance]: setelah app dimatikan
         * sistem bisa menahan service tetap hidup sementara proses kita baru start.
         */
        fun isEnabled(context: Context): Boolean {
            val expected = ComponentName(context, TombolAccessibility::class.java)
            val raw = runCatching {
                Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
                )
            }.getOrNull() ?: return false
            return raw.split(':').any { entry ->
                ComponentName.unflattenFromString(entry) == expected
            }
        }

        fun openSettings(context: Context) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            runCatching { context.startActivity(intent) }
        }
    }
}
