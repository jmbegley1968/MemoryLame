package jmb.limegreen.memorylame;

import android.app.Application;

public class MemoryLameApplication extends Application {
    private int _overallScore;

    public int getOverallScore() {
        return _overallScore;
    }

    public void setOverallScore(int score) {
        _overallScore = score;
    }

    public void incrementOverallScore(int score) {
        _overallScore = _overallScore +  score;
    }
}
