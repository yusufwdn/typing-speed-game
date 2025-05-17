# Typing Speed Game (Android)

Aplikasi Android native berbasis Java yang merupakan mini game untuk melatih kecepatan dan akurasi mengetik kata dalam waktu 60 detik. Dibangun menggunakan Android Studio dengan emulator Pixel 9 Pro API 36.

---

## Fitur

- **Game Rules**: Halaman utama menampilkan aturan permainan.
- **Gameplay**: Pemain mengetik kata yang muncul dalam waktu 60 detik, dengan skor dihitung berdasarkan kecepatan dan akurasi.
- **Game Over Screen**: Menampilkan skor akhir, jumlah kata yang berhasil diketik, akurasi, dan kata per menit.
- **Game History**: Menyimpan dan menampilkan riwayat skor sebelumnya.
- **Navigasi sederhana**: Tombol untuk mulai game, melihat history, menghapus history, dan kembali ke halaman utama.

---

## Screenshot

![Home Screen](/images/home.png)  
![Game Screen](/images/main-game.png)  
![Result Screen - Game Over](/images/result.png)  
![History](/images/history.png)  

---

## Teknologi & Tools

- Bahasa Pemrograman: Java (Native Android)
- IDE: Android Studio
- Emulator: Pixel 9 Pro API 36
- Minimum SDK: Sesuaikan dengan kebutuhan proyek (misal API 23+)

---

## Cara Menjalankan

1. Clone repository ini.
2. Buka project di Android Studio.
3. Jalankan emulator atau sambungkan perangkat Android.
4. Build dan run aplikasi menggunakan tombol **Run** di Android Studio.
5. Mainkan game dan lihat hasilnya!

---

## Struktur Aplikasi (Gambaran Singkat)

- `MainActivity` — Tampilan halaman utama (Game Rules)
- `GameActivity` — Gameplay utama (mengetik kata)
- `ScoreActivity` — Tampilan hasil akhir permainan
- `HistoryActivity` — Menampilkan riwayat skor dan data game sebelumnya
- Penyimpanan riwayat menggunakan SharedPreferences untuk menyimpan hasil di local storage android

---

## Cara Bermain

1. Tekan tombol **Start Game** pada halaman utama.
2. Ketik kata yang muncul di layar secepat dan seakurat mungkin.
3. Tekan **Submit** atau Enter setelah mengetik tiap kata.
4. Waktu 60 detik akan berjalan, dan skor akan dihitung berdasarkan kata yang benar dan akurasi.
5. Setelah waktu habis, hasil akan ditampilkan di halaman Game Over.
6. Bisa lihat riwayat hasil game di halaman History.

## Informasi Tambahan
Untuk riwayat game ini tidak menggunakan database, namun menggunakan fungsi local storage di android. Berikut script yang digunakan untuk save ke local storage tersebut.
```java
private void saveGameResult(int score, int wordsTyped, float accuracy) {
    SharedPreferences prefs = getSharedPreferences("GameHistory", MODE_PRIVATE);
    SharedPreferences.Editor editor = prefs.edit();

    // Get current date and time
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    String currentDateTime = sdf.format(new Date());

    // Get existing history
    String historyJson = prefs.getString("HISTORY", "");
    List<GameResult> gameResults = GameResult.fromJsonString(historyJson);
    
    // When result is null or empty, then assign an empty array list to gameResults variable       
    if (gameResults == null) {
        gameResults = new ArrayList<>();
    }

    // Add new result
    gameResults.add(new GameResult(currentDateTime, score, wordsTyped, accuracy));

    // Save updated history
    editor.putString("HISTORY", GameResult.toJsonString(gameResults));
    editor.apply();
}
```
Pada halaman history, ada sebuah tombol **Clear History** yang berguna untuk menghapus seluruh riwayat permainan yang dilakukan oleh pengguna. Jika melihat ke nomor 1 yang menyimpan data ke local storage, maka di sini kebalikannya yaitu menghapusnya dari penyimpanan tersebut.
```java
private void clearGameHistory() {
    SharedPreferences prefs = getSharedPreferences("GameHistory", MODE_PRIVATE);
    SharedPreferences.Editor editor = prefs.edit();
    editor.remove("HISTORY");
    editor.apply();

    // Reload the empty history
    loadGameHistory();
}
```

---

Jika ingin mencoba file `.apk`-nya tanpa ribet clone, run, dan lainnya kamu bisa di sini. 

˙✧˖°☕ ༘ ⋆｡˚ **ENJOY THIS LIFE, BE HAPPY** ˙✧˖°☕ ༘ ⋆｡˚