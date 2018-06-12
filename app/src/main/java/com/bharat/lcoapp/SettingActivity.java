package com.bharat.lcoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    private LinearLayout adLayout;
    private Boolean adPref;
    private CheckBox adCheck;
    private SeekBar fontSeekBar;
    private TextView adText;

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("Pref", MODE_PRIVATE);
        adPref = prefs.getBoolean("ad",true);
        if(adPref){
            adLayout.setVisibility(View.VISIBLE);
            adCheck.setChecked(true);
        }else {
            adLayout.setVisibility(View.INVISIBLE);
            adCheck.setChecked(false);
        }
        int fontSize = prefs.getInt("font",0);
        if(fontSize == 0){
            fontSeekBar.setProgress(0);
        }else if(fontSize == 1){
            fontSeekBar.setProgress(1);
        }else if(fontSize == 2){
            fontSeekBar.setProgress(2);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        adLayout = findViewById(R.id.ad);
        adCheck = findViewById(R.id.checkBox);
        fontSeekBar = findViewById(R.id.fontSeekBar);
        adText = findViewById(R.id.clickHere);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(450); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        adText.startAnimation(anim);

        fontSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                SharedPreferences.Editor editor = getSharedPreferences("Pref",MODE_PRIVATE).edit();
                editor.putInt("font",i);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        adLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlLCO = "https://courses.learncodeonline.in";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urlLCO));
                startActivity(intent);
            }
        });


    }


    public void onCheckboxClicked(View view){
        boolean check = ((CheckBox)view).isChecked();
        if(check){
            SharedPreferences.Editor editor = getSharedPreferences("Pref",MODE_PRIVATE).edit();
            editor.putBoolean("ad",true);
            editor.apply();
            adLayout.setVisibility(View.VISIBLE);

        }else {
            SharedPreferences.Editor editor = getSharedPreferences("Pref",MODE_PRIVATE).edit();
            editor.putBoolean("ad",false);
            editor.apply();
            adLayout.setVisibility(View.INVISIBLE);

        }

    }
}
