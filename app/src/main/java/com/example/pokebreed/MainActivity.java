package com.example.pokebreed;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    //Initialise variable
    private Spinner spinner;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //API Instance für komplette App
        APIRequests.getInstance(this);

        if(APIRequests.getInstance().requestGet("pokemon") == null) {
            Log.e("api", "immer null");
        }
        //mit api
        try {
            if (APIRequests.getInstance().getAllPoke() != null)
                pokemons = APIRequests.getInstance().getAllPoke();
            else {
                pokemons = new ArrayList<>();
                pokemons.add("test1");
                pokemons.add("test2");
                pokemons.add("test3");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        //Assign variable
        spinner = findViewById(R.id.spinner);
        textView = findViewById(R.id.textView);
        spinner.setAdapter(new ArrayAdapter<>(MainActivity.this,
               android.R.layout.simple_spinner_dropdown_item,pokemons));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if (position == 0){
                   //Display toast message
                   Toast.makeText(getApplicationContext(),
                          "Please Select one",Toast.LENGTH_SHORT).show();
                   //set empty value on textview
                   textView.setText("");
               }else{
                   //get selected value
                   String sNumber = parent.getItemAtPosition(position).toString();
                   //set selected value on textview
                   textView.setText(sNumber);
               }

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}