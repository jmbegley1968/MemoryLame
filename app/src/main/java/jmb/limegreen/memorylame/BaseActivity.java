package jmb.limegreen.memorylame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Arrays;
import java.util.List;

import jmb.limegreen.memorylame.api.MemoryGrid;
import jmb.limegreen.memorylame.api.SelectionResult;

public class BaseActivity extends AppCompatActivity {

    private MemoryGrid<Integer, Integer> _memoryGrid;
    private int _score;
    private boolean _isFinalGame;
    private Integer _card1Id;
    private boolean _ready = true;
    private ImageView _nextButtonView;
    private MediaPlayer _mpError1;
    private MediaPlayer _mpSuccess1;
    private MediaPlayer _mpSuccess2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initGame here instead?
    }

    protected void initGame(List<Integer> cards, List<Integer> faces, int nextButtonId, int adId, boolean isFinalGame) {
        // Audio
        float volume = 0.1f;
        _mpError1 = MediaPlayer.create(this, R.raw.error2);
        _mpError1.setVolume(volume, volume);
        _mpSuccess1 = MediaPlayer.create(this, R.raw.success3);
        _mpSuccess1.setVolume(volume, volume);
        _mpSuccess2 = MediaPlayer.create(this, R.raw.success2);
        _mpSuccess2.setVolume(volume, volume);

        // Memory Grid
        _memoryGrid = new MemoryGrid<>(cards, faces);

        // Next Button
        _nextButtonView = (ImageView)findViewById(nextButtonId);
        _nextButtonView.setVisibility(View.INVISIBLE);

        // Score display
        setScoreDisplay();
        _isFinalGame = isFinalGame;

        // Ad
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2755387707372913/7977110033");
        AdView mAdView = (AdView) findViewById(adId);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    protected MemoryLameApplication getMemoryLaneApp() {
        return (MemoryLameApplication)this.getApplication();
    }

    protected int getScore() {
        return _score;
    }

    public void turnCard(View view) {
        if (!_ready) {
            //showMessageDialog("Not ready.");
            return;
        }

        final ImageView imageView = (ImageView)view;
        int cardId = imageView.getId();
        SelectionResult<Integer> cardResult = _memoryGrid.revealCell(cardId);
        if (cardResult.alreadySelectedError) {
            return;
        }
        int cardFaceId = cardResult.cellValue;
        imageView.setImageResource(cardFaceId);
        boolean isPointLoss = cardResult.currentScore < _score;
        _score = cardResult.currentScore;

        if (_card1Id == null) {
            // Card 1
            _card1Id = cardId;
        } else {
            // Card 2
            if (cardResult.selectionSuccess) {
                _card1Id = null;
                if (cardResult.gridSuccess) {
                    _mpSuccess2.start();
                    if (_isFinalGame && _score == 0) {
                        showMessageDialog(this, "Perfect Score! Congratulations.");
                    }
                    _nextButtonView.setVisibility(View.VISIBLE);
                } else {
                    _mpSuccess1.start();
                }
            } else {
                _ready = false;
                if (isPointLoss) {
                    _mpError1.start();
                }
                new CountDownTimer(1000, 1000) {
                    public void onFinish() {
                        imageView.setImageResource(R.drawable.cardback2);
                        ImageView card1ImageView = (ImageView)findViewById(_card1Id);
                        card1ImageView.setImageResource(R.drawable.cardback2);
                        _card1Id = null;
                        _ready = true;
                    }
                    public void onTick(long millisUntilFinished) {
                    }
                }.start();
            }
        }

        setScoreDisplay();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_about:
                showCustomDialog(this, R.layout.about);
                return true;
            case R.id.menu_item_help:
                showCustomDialog(this, R.layout.help);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void showMessageDialog(Context context, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        //alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setIcon(R.drawable.card1);
//        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
        alertDialog.show();
    }

    protected void showCustomDialog(Context context, int layoutId) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(layoutId);
        dialog.show();
    }

    private void setScoreDisplay() {
        TextView scoreTest = (TextView)findViewById(R.id.scoreText);
        scoreTest.setText("Score: " + (getMemoryLaneApp().getOverallScore() + _score));
    }


}
