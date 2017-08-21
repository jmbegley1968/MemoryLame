package jmb.limegreen.memorylame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Arrays;
import java.util.List;

public class ActivitySixteen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixteen);

        List<Integer> cards = Arrays.asList(R.id.card1, R.id.card2, R.id.card3, R.id.card4, R.id.card5, R.id.card6, R.id.card7, R.id.card8,
                R.id.card9, R.id.card10, R.id.card11, R.id.card12, R.id.card13, R.id.card14, R.id.card15, R.id.card16);
        List<Integer> faces = Arrays.asList( R.drawable.card1,  R.drawable.card2,  R.drawable.card3,  R.drawable.card4,
                R.drawable.card5, R.drawable.card6, R.drawable.card7, R.drawable.card8);
        super.initGame(cards, faces, R.id.btnRestart, R.id.adView, true);

    }

    public void restartGame(View view) {
        Intent intent = new Intent(this, ActivityEight.class);
        super.getMemoryLaneApp().setOverallScore(0);
        startActivity(intent);
    }

}
