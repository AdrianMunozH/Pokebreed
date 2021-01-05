package com.example.pokebreed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class History extends AppCompatActivity {
    private List<Pokemon> pokemonHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        pokemonHistory = new ArrayList<>();
        Pokemon pokemon = new Pokemon("bulba");
        pokemon.setAbility("abil");
        pokemon.setAttack("atk");
        pokemon.setMother(new Pokemon("mut"));
        pokemon.setFather(new Pokemon("vat"));
        addPokemonToHistory(pokemon);

    }
    public void addPokemonToHistory(Pokemon pokemon) {
        pokemonHistory.add(pokemon);
        Log.e("pokemon json",pokemonToJson(pokemon));
    }
    public String pokemonToJson(Pokemon pokemon) {
        Gson gson = new Gson();
        return gson.toJson(pokemon);
    }
}