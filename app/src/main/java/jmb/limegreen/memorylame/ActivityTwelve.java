package jmb.limegreen.memorylame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Arrays;
import java.util.List;

public class ActivityTwelve extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twelve);

        List<Integer> cards = Arrays.asList(R.id.card1, R.id.card2, R.id.card3, R.id.card4,
                R.id.card5, R.id.card6, R.id.card7, R.id.card8, R.id.card9, R.id.card10, R.id.card11, R.id.card12);
        List<Integer> faces = Arrays.asList( R.drawable.card1,  R.drawable.card2,  R.drawable.card3,  R.drawable.card4,
                R.drawable.card5, R.drawable.card6);
        super.initGame(cards, faces, R.id.loadLevel3, R.id.adView, false);

    }

    public void loadLevel3(View view) {
        Intent intent = new Intent(this, ActivitySixteen.class);
        super.getMemoryLaneApp().incrementOverallScore(super.getScore());
        startActivity(intent);
    }

}
