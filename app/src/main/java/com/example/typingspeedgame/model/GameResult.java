package com.example.typingspeedgame.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameResult {
    private String date;
    private int score;
    private int wordsTyped;
    private float accuracy;

    public GameResult(String date, int score, int wordsTyped, float accuracy) {
        this.date = date;
        this.score = score;
        this.wordsTyped = wordsTyped;
        this.accuracy = accuracy;
    }

    // Getters
    public String getDate() { return date; }
    public int getScore() { return score; }
    public int getWordsTyped() { return wordsTyped; }
    public float getAccuracy() { return accuracy; }

    // Convert a list of GameResult objects to a JSON string
    public static String toJsonString(List<GameResult> results) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (GameResult result : results) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("date", result.date);
                jsonObject.put("score", result.score);
                jsonObject.put("wordsTyped", result.wordsTyped);
                jsonObject.put("accuracy", result.accuracy);
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }

    // Convert a JSON string to a list of GameResult objects
    public static List<GameResult> fromJsonString(String jsonString) {
        List<GameResult> results = new ArrayList<>();
        if (jsonString == null || jsonString.isEmpty()) {
            return results;
        }

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String date = jsonObject.getString("date");
                int score = jsonObject.getInt("score");
                int wordsTyped = jsonObject.getInt("wordsTyped");
                float accuracy = (float) jsonObject.getDouble("accuracy");
                results.add(new GameResult(date, score, wordsTyped, accuracy));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return results;
    }
}
