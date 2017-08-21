package jmb.limegreen.memorylame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Arrays;
import java.util.List;

public class ActivityEight extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eight);

        List<Integer> cards = Arrays.asList(R.id.card1, R.id.card2, R.id.card3, R.id.card4, R.id.card5, R.id.card6, R.id.card7, R.id.card8);
        List<Integer> faces = Arrays.asList( R.drawable.card1,  R.drawable.card2,  R.drawable.card3,  R.drawable.card4);
        super.initGame(cards, faces, R.id.loadLevel2, R.id.adView, false);

    }

    public void loadLevel2(View view) {
        Intent intent = new Intent(this, ActivityTwelve.class);
        super.getMemoryLaneApp().incrementOverallScore(super.getScore());
        startActivity(intent);
    }
}
