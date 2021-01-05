package com.example.pokebreed;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

public class MainMenu extends AppCompatActivity {
    static final String PREFS_NAME = "prefs";
    static final String PREF_DARK_THEME = "dark_theme";
    Switch modeswitch;
    ConstraintLayout layout;
    private Button newPokemon;
    private Button history;
    private Button info;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        } else {
            // Theme.AppCompat.Light.NoActionBar
            setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        layout= findViewById(R.id.myMainLayout);
        newPokemon = findViewById(R.id.newPokemon);
        history = findViewById(R.id.pokeHistory);
        info = findViewById(R.id.info);


        newPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nextActivity(MainActivity.class);

            }
        });


        /*
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(his.class);
            }
        });
         */




        modeswitch= findViewById(R.id.modeSwitch);
        if(useDarkTheme){
            //Dark Mode
            //
            modeswitch.setText("@string/Lightmode");
            layout.setBackgroundResource(R.drawable.nightmode);
        }else{
            //Light Mode
            layout.setBackgroundResource(R.drawable.daymode);
            modeswitch.setText("Darkmode");

        }

        modeswitch.setChecked(useDarkTheme);

        modeswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleTheme(isChecked);

            }
        });
    }

    private void nextActivity(Class nextClass) {
        Intent intent = new Intent(this,nextClass);
        startActivity(intent);
    }


    private void toggleTheme(boolean darkTheme) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DARK_THEME, darkTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(intent);
    }
}