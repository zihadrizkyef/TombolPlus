package com.zihadrizkyef.tombolplus.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zihadrizkyef.tombolplus.R
import com.zihadrizkyef.tombolplus.data.Prefs
import com.zihadrizkyef.tombolplus.shortcut.LockActivity
import com.zihadrizkyef.tombolplus.shortcut.PowerActivity
import com.zihadrizkyef.tombolplus.shortcut.ScreenshotActivity
import com.zihadrizkyef.tombolplus.shortcut.VolumeActivity

private class ShortcutRow(
    val label: String,
    val desc: String,
    val icon: Int,
    val target: Class<out Activity>
)

@Composable
fun AppRoot(
    a11yOk: Boolean,
    delaySec: Int,
    onOpenA11y: () -> Unit,
    onDelayChange: (Int) -> Unit,
    onRun: (Class<out Activity>) -> Unit
) {
    val rows = listOf(
        ShortcutRow(
            stringResource(R.string.shortcut_lock),
            stringResource(R.string.shortcut_lock_desc),
            R.drawable.ic_lock,
            LockActivity::class.java
        ),
        ShortcutRow(
            stringResource(R.string.shortcut_power),
            stringResource(R.string.shortcut_power_desc),
            R.drawable.ic_power,
            PowerActivity::class.java
        ),
        ShortcutRow(
            stringResource(R.string.shortcut_volume),
            stringResource(R.string.shortcut_volume_desc),
            R.drawable.ic_volume,
            VolumeActivity::class.java
        ),
        ShortcutRow(
            stringResource(R.string.shortcut_screenshot),
            stringResource(R.string.shortcut_screenshot_desc),
            R.drawable.ic_shot,
            ScreenshotActivity::class.java
        )
    )

    Box(Modifier.fillMaxSize().background(Bg), contentAlignment = Alignment.TopCenter) {
        Column(
            Modifier
                .widthIn(max = 520.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .systemBarsPadding()
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Text("Tombol+", fontSize = 30.sp, fontWeight = FontWeight.SemiBold, color = Ink)
            Spacer(Modifier.height(4.dp))
            Text(
                "Empat ikon di layar utama. Tanpa overlay, tanpa layanan latar.",
                fontSize = 13.sp,
                color = Muted
            )

            Spacer(Modifier.height(18.dp))
            StatusPanel(a11yOk = a11yOk, onOpenA11y = onOpenA11y)

            Spacer(Modifier.height(12.dp))
            DelayPanel(delaySec = delaySec, onDelayChange = onDelayChange)

            Spacer(Modifier.height(12.dp))
            Panel {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Ikon",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Ink,
                        modifier = Modifier.weight(1f)
                    )
                    Text("ketuk Coba untuk mengetes", fontSize = 11.5.sp, color = Faint)
                }
                rows.forEachIndexed { index, row ->
                    if (index > 0) {
                        Spacer(Modifier.height(2.dp))
                        Box(Modifier.fillMaxWidth().height(1.dp).background(Hair))
                        Spacer(Modifier.height(2.dp))
                    }
                    ShortcutItem(row = row, onRun = onRun)
                }
            }

            Spacer(Modifier.height(12.dp))
            Panel {
                Text(
                    "Pasang ke layar utama",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Ink
                )
                Spacer(Modifier.height(8.dp))
                Step("1", "Buka daftar aplikasi — geser ke atas dari layar utama.")
                Step("2", "Tekan lama ikon Kunci, lalu seret ke layar utama.")
                Step("3", "Ulangi untuk Power, Volume, dan Screenshot.")
                Spacer(Modifier.height(6.dp))
                Text(
                    "Kalau daftar aplikasi dimatikan, keempat ikon langsung muncul di layar utama.",
                    fontSize = 12.sp,
                    color = Faint
                )
                Spacer(Modifier.height(14.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onOpenA11y, contentPadding = PaddingValues(0.dp)) {
                        Text("Setelan aksesibilitas", fontSize = 13.sp, color = Amber, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Text(
                "Kunci, Power, dan Screenshot memakai aksi sistem Android resmi. " +
                    "Volume memakai panel volume bawaan. Tidak ada yang membaca layar.",
                fontSize = 11.5.sp,
                color = Faint
            )
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
private fun StatusPanel(a11yOk: Boolean, onOpenA11y: () -> Unit) {
    Panel {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(9.dp)
                    .background(if (a11yOk) OkGreen else Danger, CircleShape)
            )
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    if (a11yOk) "Aksesibilitas aktif" else "Aksesibilitas belum aktif",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Ink
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    if (a11yOk) "Kunci, Power, dan Screenshot siap dipakai."
                    else "Dibutuhkan untuk Kunci, Power, dan Screenshot.",
                    fontSize = 12.5.sp,
                    color = Muted
                )
            }
            if (!a11yOk) {
                TextButton(onClick = onOpenA11y) {
                    Text("Aktifkan", fontSize = 13.sp, color = Amber, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun DelayPanel(delaySec: Int, onDelayChange: (Int) -> Unit) {
    Panel {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Jeda tangkap layar",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Ink,
                modifier = Modifier.weight(1f)
            )
            Text("$delaySec detik", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Amber)
        }
        Spacer(Modifier.height(2.dp))
        Text(
            "Waktu untuk pindah ke aplikasi yang mau di-screenshot.",
            fontSize = 12.5.sp,
            color = Muted
        )
        Slider(
            value = delaySec.toFloat(),
            onValueChange = { onDelayChange(it.toInt()) },
            valueRange = Prefs.MIN_DELAY.toFloat()..Prefs.MAX_DELAY.toFloat(),
            steps = Prefs.MAX_DELAY - Prefs.MIN_DELAY - 1
        )
    }
}

@Composable
private fun ShortcutItem(row: ShortcutRow, onRun: (Class<out Activity>) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(38.dp)
                .background(SegTrack, RoundedCornerShape(11.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(row.icon),
                contentDescription = null,
                tint = Amber,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(row.label, fontSize = 14.5.sp, fontWeight = FontWeight.Medium, color = Ink)
            Spacer(Modifier.height(1.dp))
            Text(row.desc, fontSize = 12.sp, color = Muted)
        }
        TextButton(onClick = { onRun(row.target) }) {
            Text("Coba", fontSize = 13.sp, color = Amber)
        }
    }
}

@Composable
private fun Step(number: String, text: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
        Text(
            number + ".",
            fontSize = 12.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = Amber,
            modifier = Modifier.width(18.dp)
        )
        Text(text, fontSize = 12.5.sp, color = Muted)
    }
}

@Composable
private fun Panel(content: @Composable ColumnScope.() -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(CardBg, RoundedCornerShape(20.dp))
            .border(1.dp, Hair, RoundedCornerShape(20.dp))
            .padding(16.dp),
        content = content
    )
}
