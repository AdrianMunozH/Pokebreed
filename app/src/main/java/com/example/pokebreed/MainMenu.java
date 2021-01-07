package com.example.pokebreed;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.bumptech.glide.load.engine.Resource;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class MainMenu extends AppCompatActivity {
    static final String PREFS_NAME = "prefs";
    static final String PREF_DARK_THEME = "dark_theme";
    Switch modeswitch;
    ConstraintLayout layout;
    private Button newPokemon;
    private Button history;
    private Button info;

    private ImageView langImage;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_menu);
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        } else {
            // Theme.AppCompat.Light.NoActionBar
            setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        }

        super.onCreate(savedInstanceState);

        try {
            historyFile();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        layout= findViewById(R.id.myMainLayout);
        newPokemon = findViewById(R.id.newPokemon);
        history = findViewById(R.id.pokeHistory);
        info = findViewById(R.id.info);
        langImage= findViewById(R.id.langchange);

        langImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppLocale("de");
            }
        });



        newPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nextActivity(MainActivity.class);

            }
        });



        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(History.class);
            }
        });





        modeswitch= findViewById(R.id.modeSwitch);
        if(useDarkTheme){
            //Dark Mode
            //
            modeswitch.setText("Lightmode");
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
    private void historyFile() throws JSONException {
        JSONParser jsonParser = new JSONParser();
        File file = new File(JSONParser.FILE_NAME);
        // erstellt gerade noch die Datei wenn sie existiert also muss nur einmal auskommentiert werden
/*
        if(!file.exists()) {
            saveData(jsonParser.createHistoryFile().toString());
        }

*/
    }
    public void saveData(String json) {
        FileOutputStream fileOutputStream = null;
        Log.e("saveData MainMenue", "kk");
        try {
            fileOutputStream = openFileOutput(JSONParser.FILE_NAME,MODE_PRIVATE);
            // vorher lesen ???
            fileOutputStream.write(json.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    private void setAppLocale(String localecCode){
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            conf.setLocale(new Locale(localecCode.toLowerCase()));
        }else{
            res.updateConfiguration(conf,dm);
        }
    }


}