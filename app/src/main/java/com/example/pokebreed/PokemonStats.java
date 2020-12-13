package com.example.pokebreed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PokemonStats extends AppCompatActivity {
    private ImageView imageView;
    private List<String> attacks;
    private String pokemon;
    private TextView pokemonName;
    private JSONParser jp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_stats);

        imageView = (ImageView) findViewById(R.id.pokemonPic);
        pokemonName = (TextView) findViewById(R.id.pokemonName);
        attacks = new ArrayList<>();
        jp = new JSONParser();
        // hier kriegen wir die daten aus der letzten Activity
        Intent intent = getIntent();
        pokemon = intent.getStringExtra("pokeName");

        pokemonName.setText(pokemon);

        APIRequests.getInstance().requestGet(APIRequests.getInstance().getPokemon(pokemon),"pokemon");
        APIRequests.getInstance().getListeners().get("pokemon").observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                try {
                    loadPicture(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void loadPicture(JSONObject jsonObject) throws JSONException {
        // source code und doc ---  https://github.com/bumptech/glide
        Glide.with(this).load(jp.getPicture(jsonObject)).into(imageView);
    }


}