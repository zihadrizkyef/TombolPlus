package com.zihadrizkyef.tombolplus.shortcut

import com.zihadrizkyef.tombolplus.core.Actions

/**
 * Ikon "Volume": memunculkan panel volume media bawaan Android. Tidak butuh
 * aksesibilitas — AudioManager saja.
 */
class VolumeActivity : ShortcutActivity() {

    override fun perform(): Boolean {
        Actions.showVolumePanel(this)
        return true
    }
}
