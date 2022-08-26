package com.zagzoug.tictactoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class Level extends AppCompatActivity implements View.OnClickListener{

     public static final String EXTRA_DIFFICULTY ="EXTRA_DIFFICULT" ;
    public static final String SOUND = "SOUND";
    private boolean isSound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        isSound=getIntent().getBooleanExtra(StartingActivity.SOUND_UP,true);

        findViewById(R.id.easy).setOnClickListener(this);
        findViewById(R.id.difficult).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent
                =new Intent(this,PlayWithAndroid.class);
        intent.putExtra(SOUND,isSound);

        if (v.findViewById(R.id.easy)==v){
            intent.putExtra(EXTRA_DIFFICULTY,0);

            startActivity(intent);
        }
        if (v.findViewById(R.id.difficult)==v){
            intent.putExtra(EXTRA_DIFFICULTY,1);
            startActivity(intent);
        }
    }


}
