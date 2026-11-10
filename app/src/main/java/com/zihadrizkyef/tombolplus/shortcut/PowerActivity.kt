package com.zihadrizkyef.tombolplus.shortcut

import com.zihadrizkyef.tombolplus.core.Actions

/**
 * Ikon "Power": membuka power menu bawaan Android. Istri memilih sendiri
 * "Mulai ulang" atau "Matikan" di menu itu — app tidak memaksa apa pun.
 */
class PowerActivity : ShortcutActivity() {

    override fun perform(): Boolean {
        if (!accessibilityReady()) return false
        if (!Actions.powerDialog()) toastBusy()
        return true
    }
}
