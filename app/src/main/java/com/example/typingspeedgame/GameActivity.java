package com.example.typingspeedgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import androidx.core.content.res.ResourcesCompat;

import com.example.typingspeedgame.model.GameResult;
import com.example.typingspeedgame.util.FontUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private List<String> words;        // List of all possible words
    private String currentWord;        // Current word being displayed
    private int timeLeft = 60;         // Time remaining in seconds
    private int score = 0;             // Current score
    private int wordsTyped = 0;        // Number of words typed
    private int currentWordIndex = 0;      // Number of current index of word in collection
    private int correctWords = 0;      // Number of correctly typed words
    private CountDownTimer timer;      // Timer object
    private boolean isGameActive = true; // Whether game is active
    private Random random = new Random();

    // UI Components
    private TextView wordTextView, timerTextView, scoreTextView, wordsTypedTextView;
    private EditText wordInputEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enable edge-to-edge layout (for modern UI)
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        // Initialize UI Components
        wordTextView = findViewById(R.id.wordTextView);
        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        wordsTypedTextView = findViewById(R.id.wordsTypedTextView);
        wordInputEditText = findViewById(R.id.wordInputEditText);
        submitButton = findViewById(R.id.submitButton);

        // set font
        Typeface customFont = ResourcesCompat.getFont(this, R.font.press_start_to_play_regular);
        View rootView = findViewById(android.R.id.content);
        FontUtil.applyCustomFont(rootView, customFont);

        // initialize word list
        initializeWordList();

        // Set up the first word
        currentWordIndex = random.nextInt(words.size());
        currentWord = words.get(currentWordIndex);
        wordTextView.setText(currentWord);

        // Update UI
        updateUI();

        // Set up submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkWordAndContinue();
            }
        });

        // Set up keyboard "Done" action
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

        // Start the timer
        startTimer();
    }

    private void initializeWordList() {
        words = Arrays.asList(
            "Jakarta", "Tokyo", "London", "Paris", "New York",
            "Beijing", "Moscow", "Sydney", "Toronto", "Berlin",
            "Madrid", "Rome", "Seoul", "Bangkok", "Dubai",
            "Kuala Lumpur", "Cairo", "Istanbul", "Mexico City", "Buenos Aires",
            "Lima", "Nairobi", "Cape Town", "Hanoi", "Riyadh",
            "Doha", "Amsterdam", "Vienna", "Oslo", "Copenhagen",
            "Stockholm", "Helsinki", "Lisbon", "Prague", "Budapest",
            "Warsaw", "Brussels", "Athens", "Zurich", "Dublin",
            "Manila", "Taipei", "Tehran", "Baghdad", "Ankara",
            "Karachi", "New Delhi", "Islamabad", "Colombo", "Kathmandu",
            "Washington", "Ottawa", "Canberra", "Brasilia", "Santiago",
            "Quito", "Caracas", "Havana", "San Juan", "San Jose",
            "Panama City", "Montevideo", "Asuncion", "La Paz", "Sucre",
            "Pretoria", "Accra", "Abuja", "Algiers", "Tripoli",
            "Rabat", "Tunis", "Addis Ababa", "Khartoum", "Luanda",
            "Kinshasa", "Harare", "Maputo", "Lusaka", "Antananarivo",
            "Ulaanbaatar", "Tashkent", "Bishkek", "Ashgabat", "Dushanbe",
            "Yerevan", "Tbilisi", "Baku", "Kabul", "Islamabad",
            "Doha", "Manama", "Muscat", "Kuwait City", "Amman",
            "Damascus", "Beirut", "Jerusalem", "Ramallah", "Sanaa"
        );
    }

    private void startTimer() {
        timer = new CountDownTimer(timeLeft * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) (millisUntilFinished / 1000);
                timerTextView.setText("Time: " + timeLeft);
            }

            @Override
            public void onFinish() {
                isGameActive = false;
                endGame();
            }
        }.start();
    }

    private void checkWordAndContinue() {
        String userInput = wordInputEditText.getText().toString().trim();

        if (userInput.isEmpty()) {
            Toast.makeText(this, "Please type a word", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userInput.equalsIgnoreCase(currentWord)) {
            score += currentWord.length();  // Score based on word length
            correctWords++;
        }

        wordsTyped++;

        // Get next word randomly
        currentWordIndex = random.nextInt(words.size());
        currentWord = words.get(currentWordIndex);
        wordTextView.setText(currentWord);

        // Clear input
        wordInputEditText.setText("");

        // Update UI
        updateUI();
    }

    private void updateUI() {
        scoreTextView.setText("Score: " + score);
        wordsTypedTextView.setText("Words typed: " + wordsTyped);
    }

    private void endGame() {
        // Calculate accuracy
        float accuracy = wordsTyped > 0 ? ((float) correctWords / wordsTyped) * 100 : 0;

        // Save game result to SharedPreferences
        saveGameResult(score, wordsTyped, accuracy);

        // Navigate to score screen
        Intent intent = new Intent(GameActivity.this, ScoreActivity.class);
        intent.putExtra("SCORE", score);
        intent.putExtra("WORDS_TYPED", wordsTyped);
        intent.putExtra("ACCURACY", accuracy);
        startActivity(intent);

        // Finish this activity
        finish();
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}