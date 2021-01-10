package com.example.pokebreed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    private JSONParser jp;
    private JSONObject jsonObject;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MainAdapter adapter;
    private List<Pokemon> pokemonHistory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(MainMenu.PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(MainMenu.PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        } else {
            // Theme.AppCompat.Light.NoActionBar
            setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        jp = new JSONParser();

        Log.e("onCreate", String.valueOf(pokemonHistory.size()));

        jsonObject =  loadData();
        Log.e("history name of jsonObject", jsonObject.toString());
         try {
            pokemonHistory = jp.getPokemonHistory(jsonObject);
         } catch (JSONException e) {
            e.printStackTrace();
         }
         

        recyclerView = findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MainAdapter(History.this, pokemonHistory);
        recyclerView.setAdapter(adapter);
    }
     

    @Override
    protected void onStop() {
        super.onStop();
        // vor dem schließen wird die jsonHistory.json neu geschrieben um Veränderung zu speichern
        try {
            rewriteAllHistory(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("onStop", String.valueOf(pokemonHistory.size()));

    }
    public void rewriteAllHistory(JSONObject file) throws JSONException {
        JSONArray pokemonArray= file.getJSONArray("pokemonHistory");
        int size = pokemonArray.length();
        // Löscht alle Elemente
        for (int i = size; i >= 0; i--) {
            Log.e("pokemonArray", String.valueOf(pokemonArray.length() +" i: " + String.valueOf(i)));
            pokemonArray.remove(i);
        }
        int pokeSize = this.pokemonHistory.size();
        // Schreibt alle Elemente aus der Liste hinzu
        for(int i = 0; i < pokeSize; i++) {
            pokemonArray.put(jp.pokemonToJson(this.pokemonHistory.get(i)));
        }
        saveData(file.toString());
    }

    // Liest Datei aus
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

    // Schreibt in die Datei
    public JSONObject loadData() {
        FileInputStream fileInputStream = null;
        JSONObject j = new JSONObject();
        try {
            fileInputStream = openFileInput(JSONParser.FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String outText;
            while((outText = bufferedReader.readLine()) != null) {
                stringBuilder.append(outText).append("\n");
            }
            j = new JSONObject(stringBuilder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    return j;
    }
}