package com.example.pokebreed;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

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
        createNotificationChannel();
        //172800 Sec = 48 Stunden // Test mit 5/10/30 Sekunden hat geklappt
        setReminder(172800);

        // Checked ob eine Datei für die Historie existiert
        try {
            historyFile();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        layout= findViewById(R.id.myMainLayout);
        newPokemon = findViewById(R.id.newPokemon);
        history = findViewById(R.id.pokeHistory);
        info = findViewById(R.id.info);


        //Buttons für die Activity wechsel
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

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lang= Locale.getDefault().getDisplayLanguage();
                Log.e( "Language ",lang );
                if(lang.equals("Deutsch")){
                    startBrowserIntent("http://www.pokewiki.de/Zucht");
                }else{
                    startBrowserIntent("https://bulbapedia.bulbagarden.net/wiki/Pokemon_breeding");
                }

            }
        });


        // Dark-Lightmode switch
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
    // erstellt die neue Datei beim ersten mal öffnen
    private void historyFile() throws JSONException {
        JSONParser jsonParser = new JSONParser();
        File file = new File(getApplicationContext().getFilesDir(),JSONParser.FILE_NAME);
        if(!file.exists()) {
            saveData(jsonParser.createHistoryFile().toString());
        }

    }
    public void saveData(String json) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(JSONParser.FILE_NAME,MODE_PRIVATE);
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


    public void startBrowserIntent(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);

    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "PokebreedReminderChannel";
            String description = "Channel for Pokebreed Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Pokebreed",name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void setReminder(long seconds){
        Intent intent = new Intent(MainMenu.this, PokeBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainMenu.this,0,intent,0);

        AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);

        long currTime = System.currentTimeMillis();
        long calcSeconds = 1000*seconds;

        alarmManager.set(AlarmManager.RTC_WAKEUP,currTime+calcSeconds,pendingIntent);

    }
}