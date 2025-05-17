package com.example.typingspeedgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.typingspeedgame.adapter.HistoryAdapter;
import com.example.typingspeedgame.model.GameResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView historyRecyclerView;
    private TextView emptyHistoryTextView;
    private Button clearHistoryButton, homeButton;
    private HistoryAdapter historyAdapter;
    private List<GameResult> gameResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        // Initialize UI elements
        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        emptyHistoryTextView = findViewById(R.id.emptyHistoryTextView);
        clearHistoryButton = findViewById(R.id.clearHistoryButton);
        homeButton = findViewById(R.id.homeButton);

        // Set up RecyclerView
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load game history
        loadGameHistory();

        // Set up clear history button
        clearHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearGameHistory();
            }
        });

        // Set up home button
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadGameHistory() {
        SharedPreferences prefs = getSharedPreferences("GameHistory", MODE_PRIVATE);
        String historyJson = prefs.getString("HISTORY", "");

        gameResults = GameResult.fromJsonString(historyJson);
        if (gameResults == null) {
            gameResults = new ArrayList<>();
        }

        // Sort by date (newest first)
        Collections.sort(gameResults, new Comparator<GameResult>() {
            @Override
            public int compare(GameResult o1, GameResult o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        // Update UI based on whether we have history
        if (gameResults.isEmpty()) {
            historyRecyclerView.setVisibility(View.GONE);
            emptyHistoryTextView.setVisibility(View.VISIBLE);
            clearHistoryButton.setEnabled(false);
        } else {
            historyRecyclerView.setVisibility(View.VISIBLE);
            emptyHistoryTextView.setVisibility(View.GONE);
            clearHistoryButton.setEnabled(true);

            // Set up adapter
            historyAdapter = new HistoryAdapter(gameResults);
            historyRecyclerView.setAdapter(historyAdapter);
        }
    }

    private void clearGameHistory() {
        SharedPreferences prefs = getSharedPreferences("GameHistory", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("HISTORY");
        editor.apply();

        // Reload the empty history
        loadGameHistory();
    }
}