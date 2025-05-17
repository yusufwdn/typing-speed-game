package com.example.typingspeedgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {
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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);

        // Initialize UI elements
        finalScoreTextView = findViewById(R.id.finalScoreTextView);
        wordsTypedTextView = findViewById(R.id.wordsTypedTextView);
        accuracyTextView = findViewById(R.id.accuracyTextView);
        wpmTextView = findViewById(R.id.wpmTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        homeButton = findViewById(R.id.homeButton);
        viewHistoryButton = findViewById(R.id.viewHistoryButton);

        // Get data from intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        int wordsTyped = intent.getIntExtra("WORDS_TYPED", 0);
        float accuracy = intent.getFloatExtra("ACCURACY", 0);

        finalScoreTextView.setText(String.valueOf(score));
        wordsTypedTextView.setText(String.valueOf(wordsTyped));
        accuracyTextView.setText(String.format("%.1f%%", accuracy));
        wpmTextView.setText(String.valueOf(wordsTyped)); // Since our game is 60 seconds, words typed = WPM

        // Set up button click listeners
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(ScoreActivity.this, GameActivity.class);
                startActivity(gameIntent);
                finish();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(ScoreActivity.this, MainActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                finish();
            }
        });

        viewHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(ScoreActivity.this, HistoryActivity.class);
                startActivity(historyIntent);
            }
        });
    }
}