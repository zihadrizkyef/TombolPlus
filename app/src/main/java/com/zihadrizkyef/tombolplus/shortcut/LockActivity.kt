package com.zihadrizkyef.tombolplus.shortcut

import com.zihadrizkyef.tombolplus.core.Actions

/** Ikon "Kunci": matikan layar lewat GLOBAL_ACTION_LOCK_SCREEN (Android 9+). */
class LockActivity : ShortcutActivity() {

    override fun perform(): Boolean {
        if (!accessibilityReady()) return false
        if (!Actions.lock()) toastBusy()
        return true
    }
}
