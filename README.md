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

### Settingan Style dan Variabel
Kode dari warna yang digunakan dalam aplikasi ini disimpan di dalam file `colors.xml`
```xml
<!-- colors.xml -->
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="colorPrimary">#7965AF</color>
    <color name="colorPrimaryDark">#3700B3</color>
    <color name="colorAccent">#03DAC5</color>
    <color name="colorPrimaryLight">#BB86FC</color>
    <color name="black">#FF000000</color>
    <color name="white">#FFFFFFFF</color>
</resources>
```

Aplikasi ini juga menggunakan kustom font yang disetting di dalam file `styles.xml` dan `themes.xml`. Dan file font yang digunakan disimpan di `res/font/press_start_to_play_regular.ttf`
```xml
<!-- styles.xml -->
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="P2PFontStyle">
        <item name="android:fontFamily">@font/press_start_to_play_regular</item>
    </style>
</resources>
```
```xml
<!-- themes.xml -->
<resources xmlns:tools="http://schemas.android.com/tools">
    <!-- Base application theme. -->
    <style name="Base.Theme.TypingSpeedGame" parent="Theme.Material3.DayNight.NoActionBar">
        <!-- Customize your light theme here. -->
        <item name="android:fontFamily">@font/press_start_to_play_regular</item>
    </style>

    <style name="Theme.TypingSpeedGame" parent="Base.Theme.TypingSpeedGame" />
</resources>
```

### Informasi Tambahan (Teknis)

#### Main Activity
```java
package com.example.typingspeedgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    // Deklarasi tombol dan imageView yang digunakan di layout
    private Button startGameButton;
    private Button viewHistoryButton;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mengaktifkan edge-to-edge layout agar UI lebih modern dan mengikuti sistem terbaru
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Menghubungkan komponen Java dengan komponen XML menggunakan ID dari layout
        startGameButton = findViewById(R.id.startGameButton);
        viewHistoryButton = findViewById(R.id.viewHistoryButton);
        imageView = findViewById(R.id.keyboardImageView);

        // Menampilkan animasi GIF pada imageView menggunakan library Glide
        // R.raw.keyboard adalah file GIF yang disimpan di folder res/raw
        Glide.with(this).asGif().load(R.raw.keyboard).into(imageView);

        // Menambahkan listener untuk tombol "Start Game"
        // Ketika diklik, aplikasi akan berpindah ke GameActivity (halaman bermain)
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat intent dan memulai GameActivity
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        // Menambahkan listener untuk tombol "View History"
        // Ketika diklik, aplikasi akan berpindah ke HistoryActivity (halaman melihat riwayat permainan)
        viewHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat intent dan memulai HistoryActivity
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
```

#### Game Activity
```java
package com.example.typingspeedgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.typingspeedgame.model.GameResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    // ==================== Game Logic Variables ====================
    private List<String> words;                // Daftar kata yang akan digunakan dalam game
    private String currentWord;               // Kata yang sedang ditampilkan saat ini
    private int timeLeft = 60;                // Waktu tersisa dalam detik
    private int score = 0;                    // Skor saat ini
    private int wordsTyped = 0;               // Jumlah kata yang telah diketik
    private int currentWordIndex = 0;         // Index kata saat ini dalam list
    private int correctWords = 0;             // Jumlah kata yang diketik dengan benar
    private CountDownTimer timer;             // Objek timer untuk menghitung mundur waktu
    private boolean isGameActive = true;      // Status aktif/tidaknya game
    private Random random = new Random();     // Untuk memilih kata secara acak

    // ==================== UI Components ====================
    private TextView wordTextView, timerTextView, scoreTextView, wordsTypedTextView;
    private EditText wordInputEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Mengaktifkan layout edge-to-edge (UI full-screen modern)
        setContentView(R.layout.activity_game);

        // Inisialisasi komponen UI dari layout XML
        wordTextView = findViewById(R.id.wordTextView);
        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        wordsTypedTextView = findViewById(R.id.wordsTypedTextView);
        wordInputEditText = findViewById(R.id.wordInputEditText);
        submitButton = findViewById(R.id.submitButton);

        // Inisialisasi daftar kata
        initializeWordList();

        // Menampilkan kata pertama secara acak
        currentWordIndex = random.nextInt(words.size());
        currentWord = words.get(currentWordIndex);
        wordTextView.setText(currentWord);

        // Update tampilan UI awal
        updateUI();

        // Listener tombol submit → akan mengecek jawaban user
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkWordAndContinue();
            }
        });

        // Listener jika user menekan tombol "Done" di keyboard
        wordInputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkWordAndContinue();
                    return true;
                }
                return false;
            }
        });

        // Mulai timer game (60 detik)
        startTimer();
    }

    /**
     * Mengisi daftar kata-kata yang digunakan dalam game
     */
    private void initializeWordList() {
        words = Arrays.asList(
            "apple", "banana", "cherry", "date", "elderberry",
            "fig", "grape", "honeydew", "kiwi", "lemon",
            "mango", "nectarine", "orange", "papaya", "quince",
            "raspberry", "strawberry", "tangerine", "watermelon",
            "computer", "keyboard", "mouse", "monitor", "laptop",
            "software", "hardware", "internet", "website", "program",
            "algorithm", "database", "network", "server", "cloud",
            "mobile", "application", "development", "coding", "testing",
            "debug", "function", "variable", "constant", "loop",
            "array", "string", "integer", "boolean", "object"
        );
    }

    /**
     * Memulai timer hitung mundur selama 60 detik
     */
    private void startTimer() {
        timer = new CountDownTimer(timeLeft * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update waktu setiap detik
                timeLeft = (int) (millisUntilFinished / 1000);
                timerTextView.setText("Time: " + timeLeft);
            }

            @Override
            public void onFinish() {
                // Timer selesai → akhiri game
                isGameActive = false;
                endGame();
            }
        }.start();
    }

    /**
     * Mengecek apakah kata yang diketik user benar, update skor, dan lanjut ke kata berikutnya
     */
    private void checkWordAndContinue() {
        String userInput = wordInputEditText.getText().toString().trim();

        // Jika input kosong, beri peringatan
        if (userInput.isEmpty()) {
            Toast.makeText(this, "Please type a word", Toast.LENGTH_SHORT).show();
            return;
        }

        // Jika jawaban benar (case-insensitive), tambahkan skor
        if (userInput.equalsIgnoreCase(currentWord)) {
            score += currentWord.length(); // Skor berdasarkan panjang kata
            correctWords++;
        }

        wordsTyped++; // Tambah jumlah kata yang diketik (benar atau salah)

        // Ambil kata berikutnya secara acak
        currentWordIndex = random.nextInt(words.size());
        currentWord = words.get(currentWordIndex);
        wordTextView.setText(currentWord);

        // Kosongkan kolom input
        wordInputEditText.setText("");

        // Update tampilan skor dan jumlah kata
        updateUI();
    }

    /**
     * Mengupdate UI: skor dan total kata diketik
     */
    private void updateUI() {
        scoreTextView.setText("Score: " + score);
        wordsTypedTextView.setText("Words typed: " + wordsTyped);
    }

    /**
     * Menyimpan hasil game ke SharedPreferences dan navigasi ke ScoreActivity
     */
    private void endGame() {
        // Hitung akurasi (jumlah benar / jumlah total diketik)
        float accuracy = wordsTyped > 0 ? ((float) correctWords / wordsTyped) * 100 : 0;

        // Simpan hasil ke SharedPreferences
        saveGameResult(score, wordsTyped, accuracy);

        // Kirim data ke halaman skor dan mulai activity baru
        Intent intent = new Intent(GameActivity.this, ScoreActivity.class);
        intent.putExtra("SCORE", score);
        intent.putExtra("WORDS_TYPED", wordsTyped);
        intent.putExtra("ACCURACY", accuracy);
        startActivity(intent);

        // Selesai dengan activity saat ini
        finish();
    }

    /**
     * Menyimpan hasil permainan ke SharedPreferences dalam format JSON
     */
    private void saveGameResult(int score, int wordsTyped, float accuracy) {
        SharedPreferences prefs = getSharedPreferences("GameHistory", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Ambil tanggal dan waktu saat ini
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateTime = sdf.format(new Date());

        // Ambil history sebelumnya dalam bentuk JSON
        String historyJson = prefs.getString("HISTORY", "");
        List<GameResult> gameResults = GameResult.fromJsonString(historyJson);

        // Jika belum ada history sebelumnya, buat list baru
        if (gameResults == null) {
            gameResults = new ArrayList<>();
        }

        // Tambahkan hasil game terbaru ke list
        gameResults.add(new GameResult(currentDateTime, score, wordsTyped, accuracy));

        // Simpan list hasil game sebagai JSON string
        editor.putString("HISTORY", GameResult.toJsonString(gameResults));
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Pastikan timer dihentikan jika activity dihancurkan
        if (timer != null) {
            timer.cancel();
        }
    }
}
```

#### Score / Result Activity
```java
package com.example.typingspeedgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {
    // Deklarasi komponen UI
    private TextView finalScoreTextView;
    private TextView wordsTypedTextView;
    private TextView accuracyTextView;
    private TextView wpmTextView;
    private Button playAgainButton;
    private Button homeButton;
    private Button viewHistoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Mengaktifkan edge-to-edge layout (tampilan layar penuh yang modern)
        setContentView(R.layout.activity_score);

        // Menghubungkan komponen UI dari layout XML ke variabel Java
        finalScoreTextView = findViewById(R.id.finalScoreTextView);
        wordsTypedTextView = findViewById(R.id.wordsTypedTextView);
        accuracyTextView = findViewById(R.id.accuracyTextView);
        wpmTextView = findViewById(R.id.wpmTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        homeButton = findViewById(R.id.homeButton);
        viewHistoryButton = findViewById(R.id.viewHistoryButton);

        // Mengambil data hasil permainan yang dikirim dari GameActivity lewat Intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);               // Skor akhir
        int wordsTyped = intent.getIntExtra("WORDS_TYPED", 0);   // Jumlah kata yang diketik
        float accuracy = intent.getFloatExtra("ACCURACY", 0);    // Akurasi dalam persen

        // Menampilkan data hasil permainan ke tampilan layar
        finalScoreTextView.setText(String.valueOf(score));
        wordsTypedTextView.setText(String.valueOf(wordsTyped));
        accuracyTextView.setText(String.format("%.1f%%", accuracy));

        // Karena game hanya berdurasi 60 detik, jumlah kata yang diketik bisa dianggap sebagai WPM (Words Per Minute)
        wpmTextView.setText(String.valueOf(wordsTyped));

        // Ketika tombol "Play Again" ditekan, mulai ulang permainan
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(ScoreActivity.this, GameActivity.class);
                startActivity(gameIntent);
                finish(); // Menutup halaman skor agar tidak bisa kembali dengan tombol Back
            }
        });

        // Ketika tombol "Home" ditekan, kembali ke halaman utama
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(ScoreActivity.this, MainActivity.class);
                // Membersihkan semua activity di atas MainActivity dari stack
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                finish(); // Menutup halaman skor
            }
        });

        // Ketika tombol "View History" ditekan, buka halaman riwayat permainan
        viewHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(ScoreActivity.this, HistoryActivity.class);
                startActivity(historyIntent);
            }
        });
    }
}

```

#### History Activity
```java
package com.example.typingspeedgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.typingspeedgame.adapter.HistoryAdapter;
import com.example.typingspeedgame.model.GameResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    // Komponen UI
    private RecyclerView historyRecyclerView;
    private TextView emptyHistoryTextView;
    private Button clearHistoryButton, homeButton;

    // Adapter dan list data
    private HistoryAdapter historyAdapter;
    private List<GameResult> gameResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Mengaktifkan tampilan edge-to-edge
        setContentView(R.layout.activity_history);

        // Inisialisasi komponen tampilan
        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        emptyHistoryTextView = findViewById(R.id.emptyHistoryTextView);
        clearHistoryButton = findViewById(R.id.clearHistoryButton);
        homeButton = findViewById(R.id.homeButton);

        // Menentukan layout RecyclerView sebagai Linear (vertikal)
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Memuat data histori permainan dari SharedPreferences
        loadGameHistory();

        // Tombol untuk menghapus semua histori
        clearHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearGameHistory(); // Menghapus data histori
            }
        });

        // Tombol untuk kembali ke halaman utama
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Fungsi untuk memuat histori permainan dari SharedPreferences
    private void loadGameHistory() {
        SharedPreferences prefs = getSharedPreferences("GameHistory", MODE_PRIVATE);
        String historyJson = prefs.getString("HISTORY", "");

        // Mengubah JSON string menjadi list of GameResult
        gameResults = GameResult.fromJsonString(historyJson);
        if (gameResults == null) {
            gameResults = new ArrayList<>();
        }

        // Urutkan data dari yang terbaru ke yang lama berdasarkan tanggal
        Collections.sort(gameResults, new Comparator<GameResult>() {
            @Override
            public int compare(GameResult o1, GameResult o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        // Jika tidak ada histori, tampilkan pesan kosong
        if (gameResults.isEmpty()) {
            historyRecyclerView.setVisibility(View.GONE);
            emptyHistoryTextView.setVisibility(View.VISIBLE);
            clearHistoryButton.setEnabled(false);
        } else {
            // Tampilkan RecyclerView dan sembunyikan teks kosong
            historyRecyclerView.setVisibility(View.VISIBLE);
            emptyHistoryTextView.setVisibility(View.GONE);
            clearHistoryButton.setEnabled(true);

            // Pasang adapter untuk menampilkan list data
            historyAdapter = new HistoryAdapter(gameResults);
            historyRecyclerView.setAdapter(historyAdapter);
        }
    }

    // Fungsi untuk menghapus semua histori dari SharedPreferences
    private void clearGameHistory() {
        SharedPreferences prefs = getSharedPreferences("GameHistory", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("HISTORY"); // Hapus data berdasarkan key
        editor.apply();

        // Muat ulang tampilan dengan data kosong
        loadGameHistory();
    }
}

```


Jika ingin mencoba file `.apk`-nya tanpa ribet clone, run, dan lainnya kamu bisa [di sini](/app/release/typingspeed.apk). 

˙✧˖°☕ ༘ ⋆｡˚ **ENJOY THIS LIFE, BE HAPPY** ˙✧˖°☕ ༘ ⋆｡˚