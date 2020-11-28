package com.example.pokebreed;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Kommentar von Daniel - ASDf
    public List<String> pokemons;

    String selectedMon="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mit api
        pokemons = new ArrayList<>();
        fillPokemons();

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        //Funktioniert mit string-arrays aus den resourcen
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.strarr,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Funktioniert mit Arrays
        String[] str = {"erste","zweite"};
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,str);

        ArrayAdapter listAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,pokemons);


        spinner.setAdapter(listAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // gibt das ausgewählte element aus
                Log.e("spinner",spinner.getSelectedItem().toString());
                selectedMon=spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void fillPokemons() {
        final TextView textView = (TextView) findViewById(R.id.textView);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://pokeapi.co/api/v2/pokemon?limit=800";
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length();i++) {
                        JSONObject pokemon = jsonArray.getJSONObject(i);
                        String name = pokemon.getString("name");
                        pokemons.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("Rest","rest erfolgreich");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error);
            }
        });
        queue.add(json);
    }
}