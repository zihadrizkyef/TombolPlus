# Tombol+

Empat ikon terpisah di layar utama Android untuk HP yang tombol fisiknya (power / volume) sudah tidak bisa dipakai.
**Tanpa overlay**, tanpa root, tanpa Shizuku — dan tanpa izin "Tampil di atas aplikasi".

| Ikon | Yang terjadi |
|---|---|
| **Kunci** | Layar langsung mati (`GLOBAL_ACTION_LOCK_SCREEN`, Android 9+) |
| **Power** | Muncul power menu bawaan Android (`GLOBAL_ACTION_POWER_DIALOG`); user memilih "Mulai ulang" / "Matikan" |
| **Volume** | Muncul panel volume media bawaan Android (`AudioManager` + `FLAG_SHOW_UI`); slidernya bisa digeser |
| **Screenshot** | Tangkap layar setelah jeda 5 detik (`GLOBAL_ACTION_TAKE_SCREENSHOT`, Android 11+) — jeda itu dipakai untuk pindah ke aplikasi yang mau ditangkap |

Satu activity tanpa UI per ikon: dibuka → aksi dipicu → activity menutup diri. Yang selalu terlihat
di layar adalah UI bawaan Android, bukan tampilan app ini.

## Izin (1 saja)

**Aksesibilitas** — untuk Kunci, Power, dan Screenshot. Layanan ini tidak membaca layar dan
tidak merekam apa pun (`canRetrieveWindowContent="false"`, hanya `typeWindowStateChanged`).
Volume tidak butuh izin apa pun.

## Alur screenshot

1. Buka aplikasi yang mau ditangkap.
2. Ke layar utama, ketuk ikon **Screenshot**.
3. Kembali ke aplikasi tadi (dalam 5 detik).
4. Android mengambil tangkapan layar seperti menekan tombol fisik.

Jeda bisa diubah 3–15 detik di layar Tombol+.

## Build

```bash
./gradlew :app:assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Butuh JDK 17 dan Android SDK dengan platform 36. `local.properties` menunjuk ke `D:\Android\Sdk`.

## Struktur

```
app/src/main/java/com/zihadrizkyef/tombolplus/
├── MainActivity.kt                # layar panduan: status izin, jeda, tombol uji
├── core/Actions.kt                # kunci / power / volume / screenshot
├── data/Prefs.kt                  # jeda tangkap layar
├── shortcut/
│   ├── ShortcutActivity.kt        # dasar: jalankan aksi lalu tutup diri
│   ├── LockActivity.kt
│   ├── PowerActivity.kt
│   ├── VolumeActivity.kt
│   └── ScreenshotActivity.kt
├── service/TombolAccessibility.kt # pemegang izin + timer tangkap layar
└── ui/App.kt                      # Compose, satu layar
```

Keempat activity shortcut punya `LAUNCHER` intent-filter masing-masing, jadi muncul sebagai
ikon sendiri di daftar aplikasi dan bisa diseret ke layar utama.

## Catatan

- Ikon shortcut: latar gelap `#0D0F12` + glyph amber `#FFB020` (adaptive icon, ada versi monokrom).
- Activity shortcut memakai `Theme.TombolPlus.Shortcut` (tembus pandang, tanpa animasi),
  `taskAffinity=""`, dan `finishAndRemoveTask()` supaya tidak meninggalkan jejak di recents.
- Kalau aksesibilitas mati, ikon yang membutuhkannya menampilkan toast lalu membuka layar panduan.
- Konsep overlay (rail di tepi kanan layar) sudah dibuang: izin "Tampil di atas aplikasi"
  tidak bisa dipakai di HP target (HyperOS membatasi izin itu untuk app sideload).

## FAQ

**Di halaman aksesibilitas ada baris "Tombol+" dan "Shortcut". "Shortcut" itu apa?**
Baris itu bawaan Android Settings untuk setiap service aksesibilitas, bukan dari app ini.
Isinya menetapkan service sebagai target **tombol pintasan aksesibilitas sistem** (ikon
mengambang yang digambar Android sendiri). Untuk Tombol+ **biarkan Off** — service ini tidak
mendaftarkan handler tombol, jadi tombolnya tidak melakukan apa pun.

**Kenapa tidak pakai tombol mengambang itu saja?**
Bisa — itu jalan sah untuk tombol melayang tanpa izin overlay, karena tombolnya digambar
sistem. Butuh `flagRequestAccessibilityButton` + `onAccessibilityButtonClicked` di service.
Konsep sekarang sengaja memakai 4 ikon launcher supaya tidak ada apa pun menutupi layar.

**Sudah dites di mana?**
OPPO CPH2061 (Android 11) dan Redmi 23129RN51X (Android 16 / HyperOS V140): Kunci, Power,
Volume (panel `ColorVolumeDialogImpl` muncul), dan Screenshot (delay 5 detik) berjalan.

