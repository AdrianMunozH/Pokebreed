package com.example.pokebreed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class History extends AppCompatActivity {
    private JSONParser jp;
    private JSONObject jsonObject;
    private TextView tv;
    private TextView delete1;
    private TextView delete2;
    private List<Pokemon> recentlyAdded = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        jp = new JSONParser();
        tv = findViewById(R.id.jsonText);

        //deleteLater
        delete1 = findViewById(R.id.delete1);
        delete2 = findViewById(R.id.delete2);

         jsonObject =  loadData();
        Log.e("history name of jsonObject", jsonObject.toString());

        List<Pokemon> pokemonHistory = new ArrayList<>();
         try {
            pokemonHistory = jp.getPokemonHistory(jsonObject);
         } catch (JSONException e) {
            e.printStackTrace();
         }
         delete1.setText(pokemonHistory.get(0).getName());
         delete2.setText(pokemonHistory.get(1).getName());

        tv.setText(pokemonHistory.get(0).getName() + " : " + pokemonHistory.get(1).getName());

    }

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
            Log.e("history loaddata1",stringBuilder.toString());
            j = new JSONObject(stringBuilder.toString());
            //hier muss der jsonparser benutzt werden
            Log.e("json object", j.toString());
            // text nehmen mit stringBuilder.toString()
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