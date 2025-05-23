package com.example.typingspeedgame;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.example.typingspeedgame.util.FontUtil;

public class MainActivity extends AppCompatActivity {
    private Button startGameButton;
    private Button viewHistoryButton;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize UI element
        startGameButton = findViewById(R.id.startGameButton);
        viewHistoryButton = findViewById(R.id.viewHistoryButton);
        imageView = findViewById(R.id.keyboardImageView);

        // set font
        Typeface customFont = ResourcesCompat.getFont(this, R.font.press_start_to_play_regular);
        View rootView = findViewById(android.R.id.content);
        FontUtil.applyCustomFont(rootView, customFont);

        // Set image
        Glide.with(this).asGif().load(R.raw.keyboard).into(imageView);

        // Set click listeners
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to game screen
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        viewHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to history screen
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}