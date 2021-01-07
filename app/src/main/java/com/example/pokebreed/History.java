package com.example.pokebreed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        /*
        Pokemon pokemon = new Pokemon("bulba");
        pokemon.setAbility("abil");
        pokemon.setAttack("atk");
        pokemon.setMother(new Pokemon("mut"));
        pokemon.setFather(new Pokemon("vat"));
        addPokemonToHistory(pokemon);

         */
        loadData();

    }
    private void loadDataJSON() {
        FileInputStream fileInputStream = null;
        Gson gson = new Gson();


        try {
            fileInputStream = openFileInput(JSONParser.FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            JsonReader jsonReader = new JsonReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String outText;
            while((outText = bufferedReader.readLine()) != null) {
                stringBuilder.append(outText).append("\n");
            }
            Log.e("history loaddata",stringBuilder.toString());
            JSONObject j = new JSONObject(stringBuilder.toString());
            jp.getAllAbilities(j);
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

    }
    public void testJson(JSONObject jsonObject) throws JSONException {

    }

    private void loadData() {
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = openFileInput(JSONParser.FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String outText;
            while((outText = bufferedReader.readLine()) != null) {
                stringBuilder.append(outText).append("\n");
            }
            Log.e("history loaddata",stringBuilder.toString());

            // text nehmen mit stringBuilder.toString()
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

    }



}