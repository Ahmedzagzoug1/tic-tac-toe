package com.zagzoug.tictactoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class StartingActivity extends AppCompatActivity {

    public static final String SOUND_UP ="SOUND_UP" ;
    private static final int START_LEVEL = 1;


    private int mLevel;
    private Button mNextLevelButton;
    private TextView mLevelTextView;

    ImageButton imageButton;

    boolean soundUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);


        SharedPreferences sharedPreferences=getSharedPreferences("RESULT",MODE_PRIVATE);

        soundUp=sharedPreferences.getBoolean(SOUND_UP,true);

    imageButton= (ImageButton) findViewById(R.id.image_button);
        if (!soundUp){
            imageButton.setImageResource(R.drawable.ic_volume_off_black);

        }else {
            imageButton.setImageResource(R.drawable.ic_volume_up_black);

        }

        // add ads


        //interestial





    }

    public void withComputer(View view) {
        Intent intent=new Intent(this,Level.class);
        intent.putExtra(SOUND_UP,soundUp);
        startActivity(intent);
    }

    public void withSomeOne(View view) {
        Intent intent=new Intent(this,PlayAginstAnthor.class);
    intent.putExtra(SOUND_UP,soundUp);
        startActivity(intent);
    }

    public void about(View view) {
        Intent intent=new Intent(this,About.class);
        startActivity(intent);
    }

    public void setSound(View view) {
        if (soundUp){
            imageButton.setImageResource(R.drawable.ic_volume_off_black);
            soundUp =!soundUp;
        }else {
            imageButton.setImageResource(R.drawable.ic_volume_up_black);
            soundUp =!soundUp;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences=getSharedPreferences("RESULT",MODE_PRIVATE);

            sharedPreferences.edit().putBoolean(SOUND_UP,soundUp).apply();
    }

}





