package com.zagzoug.tictactoc;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class PlayWithAndroid extends AppCompatActivity implements View.OnClickListener {

    private static final String RESULTEASY1 ="RESULTEASY1" ;
    private static final String RESULTEASY2 ="RESULTEASY2" ;
    private static final String RESULTDIFFICULT1 ="RESULTDIFFICULT1" ;
    private static final String RESULTDIFFICULT2 ="RESULTDIFFICULT2" ;


    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int playerAndroidPoints;


    private TextView textViewPlayer1;
    private TextView textViewPlayerAndroid;

    private int difficulty;

    String[][] emptyButtons = new String[3][3];

    private MediaPlayer p1,p2,win;
    private boolean isSound;

    InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_android);

        p1=MediaPlayer.create(this, R.raw.p1);
        p2=MediaPlayer.create(this, R.raw.p2);
        win=MediaPlayer.create(this, R.raw.game_win2);

        difficulty=getIntent().getIntExtra(Level.EXTRA_DIFFICULTY,0);
        isSound=getIntent().getBooleanExtra(Level.SOUND,true);

        textViewPlayer1 = (TextView) findViewById(R.id.tv_auto_player_1);

        textViewPlayerAndroid = (TextView) findViewById(R.id.tv_auto_player_2);

        SharedPreferences sharedPreferences=getSharedPreferences("RESULT",MODE_PRIVATE);
        if (difficulty==0) {
            player1Points = sharedPreferences.getInt(RESULTEASY1, 0);
            playerAndroidPoints = sharedPreferences.getInt(RESULTEASY2, 0);
            updatePointsText();
        }else if (difficulty==1) {
            player1Points = sharedPreferences.getInt(RESULTDIFFICULT1, 0);
            playerAndroidPoints = sharedPreferences.getInt(RESULTDIFFICULT2, 0);
            updatePointsText();
        }


        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                String buttonID = "bu_" + i + j;

                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = (Button) findViewById(resID);
                buttons[i][j].setOnClickListener(this);

            }
        }


        Button buttonReset = (Button) findViewById(R.id.reset_android);

        buttonReset.setOnClickListener(new View.OnClickListener() {

            @Override


            public void onClick(View v) {

                resetGame();
            }
        });

  /*      MobileAds.initialize(this , getResources().getString(R.string.application_id));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_android_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
               if (mInterstitialAd.isLoaded())
                   mInterstitialAd.show();
            }
        });*/


        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
        //mInterstitialAd
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_android_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed(){
                super.onAdClosed();
                finish();
            }
        });

    }


    private void player1Wins() {

        player1Points++;

        Toast.makeText(this, "I win!", Toast.LENGTH_SHORT).show();
        if (isSound)
            win.start();
        new CountDownTimer(3000,200){

            @Override
            public void onTick(long millisUntilFinished) {


            }

            @Override
            public void onFinish() {
                updatePointsText();
                resetBoard();
            }
        }.start();

    }

    private void player2Wins() {

        playerAndroidPoints++;

        Toast.makeText(this, "Android wins!", Toast.LENGTH_SHORT).show();
        if (isSound)
            win.start();
        new CountDownTimer(3000,200){

            @Override
            public void onTick(long millisUntilFinished) {


            }

            @Override
            public void onFinish() {
                updatePointsText();
                resetBoard();
            }
        }.start();

    }
    private void draw() {

        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();

        resetBoard();

    }

    private boolean checkForWin() {

        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                field[i][j] = buttons[i][j].getText().toString();
            }}

        for (int i = 0; i < 3; i++) {

            if (field[i][0].equals(field[i][1])

                    &&field[i][0].equals(field[i][2])

                    &&!field[i][0].equals("")){
                return true;

            }}



        for (int i = 0; i < 3; i++) {

            if (field[0][i].equals(field[1][i])

                    &&field[0][i].equals(field[2][i])

                    &&!field[0][i].equals("")){

                return true;

            }

        }



        if (field[0][0].equals(field[1][1])

                &&field[0][0].equals(field[2][2])

                &&!field[0][0].equals("")){

            return true;

        }



        if (field[0][2].equals(field[1][1])

                &&field[0][2].equals(field[2][0])

                &&!field[0][2].equals("")){

            return true;

        }



        return false;

    }

    private void updatePointsText() {

        textViewPlayer1.setText("I : " + player1Points);
        textViewPlayerAndroid.setText("Android : " + playerAndroidPoints);

    }

    private void resetBoard() {

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                buttons[i][j].setText("");

            }}
        roundCount = 0;

        player1Turn = true;}

    private void resetGame() {

        player1Points = 0;

        playerAndroidPoints = 0;

        updatePointsText();

        resetBoard();

    }

    @Override



    public void onClick(View v) {

        if (!((Button) v).getText().toString().equals("")) {

            return;

        }
        if (isSound)
            p1.start();

        ((Button) v).setText("X");



        roundCount++;
        if (checkForWin()) {

            player1Wins();

        }

        else if (roundCount == 9) {

            draw();

        } else {

            player1Turn = !player1Turn;


            new CountDownTimer(300,200){

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    if (isSound)
                        p2.start();
                    playGames();
                }
            }.start();

        }
    }

    private void playGames() {
        Button  buttonSelect;
        if (difficulty==0){
            buttonSelect=getRandom();

            buttonSelect.setText("O");

        }else {
            buttonSelect=getSafe();
            buttonSelect.setText("O");

        }

        roundCount++;
        if (checkForWin()) {

            player2Wins();

        }

        else if (roundCount == 9) {

            draw();

        } else {

            player1Turn = !player1Turn;
        }
    }

    private Button getSafe() {

        Button selectedButton;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                emptyButtons[i][j]=buttons[i][j].getText().toString();
            }}

        //probability of defence
        if (emptyButtons[0][0].equals("")){
            if (checkSafe(0,1,0,2)||checkSafe(1,1,2,2)||checkSafe(1,0,2,0)) {

                selectedButton = buttons[0][0];
                return selectedButton;
            }
        }

        if (emptyButtons[0][1].equals("")){
            if (checkSafe(0,0,0,2)||checkSafe(1,1,2,1)) {

                selectedButton = buttons[0][1];
                return selectedButton;
            }
        }
        if (emptyButtons[0][2].equals("")){
            if (checkSafe(0,0,0,1)||checkSafe(1,1,2,0)||checkSafe(1,2,2,2)) {

                selectedButton = buttons[0][2];
                return selectedButton;
            }
        }
        if (emptyButtons[1][0].equals("")){
            if (checkSafe(0,0,2,0)||checkSafe(1,1,1,2)){
                selectedButton = buttons[1][0];
                return selectedButton;
            }
        }

        if (emptyButtons[1][1].equals("")){
            if (checkSafe(0,0,2,2)||checkSafe(0,2,2,0)||checkSafe(1,0,1,2)||checkSafe(0,1,2,1)) {

                selectedButton = buttons[1][1];
                return selectedButton;
            }
        }

        if (emptyButtons[1][2].equals("")){
            if (checkSafe(1,0,1,1)||checkSafe(0,2,2,2)){
                selectedButton = buttons[1][2];
                return selectedButton;
            }
        }

        if (emptyButtons[2][0].equals("")){
            if (checkSafe(0,0,1,0)||checkSafe(2,1,2,2)||checkSafe(1,1,0,2)) {

                selectedButton = buttons[2][0];
                return selectedButton;
            }
        }

        if (emptyButtons[2][1].equals("")){
            if (checkSafe(2,0,2,2)||checkSafe(0,1,1,2)) {

                selectedButton = buttons[2][1];
                return selectedButton;
            }
        }

        if (emptyButtons[2][2].equals("")){
            if (checkSafe(0,0,1,1)||checkSafe(1,2,0,2)||checkSafe(1,1,0,0)) {

                selectedButton = buttons[2][2];
                return selectedButton;
            }
        }

        selectedButton=getRandom();
        return selectedButton;
    }

    private boolean checkSafe(int f1, int f2, int s1, int s2) {
        if (emptyButtons[f1][f2].equals(emptyButtons[s1][s2]) &&
                (emptyButtons[f1][f2].equals("X")||emptyButtons[f1][f2].equals("O"))){
            return true;
        }else
            return false;
    }

    private Button getRandom() {
        ArrayList<Button> emptyButtons = new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().equals("")){
                    emptyButtons.add(buttons[i][j]);
                }

            }
        }

        Random r=new Random();
        int  RandIndex=r.nextInt(emptyButtons.size()- 0)+ 0; // if size =3 , select (0,1,2)
        return emptyButtons.get(RandIndex);

    }

    @Override


    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);



        outState.putInt("roundCount", roundCount);

        outState.putInt("player1Points", player1Points);

        outState.putInt("player2Points", playerAndroidPoints);

        outState.putBoolean("player1Turn", player1Turn);

    }



    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);



        roundCount = savedInstanceState.getInt("roundCount");

        player1Points = savedInstanceState.getInt("player1Points");

        playerAndroidPoints = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences=getSharedPreferences("RESULT",MODE_PRIVATE);
        if (difficulty==0)
            sharedPreferences.edit().putInt(RESULTEASY1,player1Points)
                    .putInt(RESULTEASY2,playerAndroidPoints).apply();

        else if(difficulty==1)
            sharedPreferences.edit().putInt(RESULTDIFFICULT1,player1Points)
                    .putInt(RESULTDIFFICULT2,playerAndroidPoints).apply();
    }


    public void showInterestial(){
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }
        else{
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showInterestial();
    }

}
