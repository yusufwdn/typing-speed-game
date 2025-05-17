package com.example.typingspeedgame.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.typingspeedgame.R;
import com.example.typingspeedgame.model.GameResult;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<GameResult> gameResults;

    public HistoryAdapter(List<GameResult> gameResults) {
        this.gameResults = gameResults;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        GameResult result = gameResults.get(position);
        holder.dateTextView.setText(result.getDate());
        holder.scoreTextView.setText("Score: " + result.getScore());
        holder.wordsTextView.setText("Words: " + result.getWordsTyped());
        holder.accuracyTextView.setText(String.format("Accuracy: %.1f%%", result.getAccuracy()));
    }

    @Override
    public int getItemCount() {
        return gameResults.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView scoreTextView;
        TextView wordsTextView;
        TextView accuracyTextView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
            wordsTextView = itemView.findViewById(R.id.wordsTextView);
            accuracyTextView = itemView.findViewById(R.id.accuracyTextView);
        }
    }
}
