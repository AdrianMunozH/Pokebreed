package com.example.pokebreed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProtoAttackWesen extends AppCompatActivity {
    private Spinner sAttack;
    private Spinner sWesen;
    private TextView tvAttack;
    private TextView tvWesen;
    private String pokemon;
    private List<String> attacks;
    JSONParser jp = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proto_attack_wesen);

        sAttack = (Spinner) findViewById(R.id.sAttack);
        sWesen = (Spinner) findViewById(R.id.sWesen);
        tvAttack = (TextView) findViewById(R.id.tvAttack);
        tvWesen = (TextView) findViewById(R.id.tvWesen);
        attacks = new ArrayList<>();

        Intent intent = getIntent();
        pokemon = intent.getStringExtra("pokeName");

        tvAttack.setText(pokemon);

        APIRequests.getInstance().requestGet(APIRequests.getInstance().getPokemon(pokemon));
        APIRequests.getInstance().listen.observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    Log.e("onChangedAttack","succ");
                    attacks = jp.getAllAttacks(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sAttack.setAdapter(new ArrayAdapter<>(ProtoAttackWesen.this,
                        android.R.layout.simple_spinner_dropdown_item,attacks));
            }
        });

        sAttack.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    //Display toast message
                    Toast.makeText(getApplicationContext(),
                            "Please Select one",Toast.LENGTH_SHORT).show();
                    //set empty value on textview
                    tvAttack.setText("");
                }else{
                    //get selected value
                    String sNumber = parent.getItemAtPosition(position).toString();
                    //set selected value on textview
                    tvAttack.setText(sNumber);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}