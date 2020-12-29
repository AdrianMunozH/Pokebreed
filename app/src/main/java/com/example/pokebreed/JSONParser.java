package com.example.pokebreed;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {


    public List<String> getAllPoke(JSONObject jsonObject) throws JSONException {
        List<String> pokemonList = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject pokemon = jsonArray.getJSONObject(i);
                String name = pokemon.getString("name");
                pokemonList.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pokemonList;
    }
    public List<String> getPokemon(JSONObject jsonObject) {
        return null;
    }

    public List<String> getAllAbilities(JSONObject jsonObject) throws JSONException {
        List<String> abilityList = new ArrayList<>();
        JSONArray abilities = jsonObject.getJSONArray("abilities");
        Log.e("abilites", "length " + abilities.length() + " : " + abilities.toString());
        for (int i = 0; i < abilities.length();i++) {
            JSONObject ability = abilities.getJSONObject(i).getJSONObject("ability");
            Log.e("ability" + i, ability.toString());
            abilityList.add(ability.getString("name"));
        }
        return  abilityList;
    }
    public List<String> getAllAttacks(JSONObject jsonObject) throws JSONException {
        List<String> movesList = new ArrayList<>();
        JSONArray moves = jsonObject.getJSONArray("moves");
        for (int i = 0; i < moves.length();i++) {
            JSONObject move = moves.getJSONObject(i).getJSONObject("move");
            movesList.add(move.getString("name"));
        }
        return  movesList;
    }
    public List<String> getPokemonOfType(JSONObject jsonObject) throws JSONException {
        List<String> pokemonList = new ArrayList<>();
        JSONArray pokemon = jsonObject.getJSONArray("pokemon");
        Log.e("ofType",pokemon.toString());
        for (int i = 0; i < pokemon.length();i++) {
            JSONObject poke = pokemon.getJSONObject(i).getJSONObject("pokemon");
            Log.e("TypePokemon",poke.toString());
            pokemonList.add(poke.getString("name"));
        }
        return  pokemonList;
    }
    public String getPicture(JSONObject jsonObject) throws JSONException {
        String pictureUrl;
        JSONObject pokemon = jsonObject.getJSONObject("sprites");
        pictureUrl = pokemon.getString("front_default");
        return pictureUrl;
    }

    public List<String> getEggGroups(JSONObject jsonObject) throws JSONException {
        List<String> eggGroupEntries = new ArrayList<>();
        JSONArray eggGrp = jsonObject.getJSONArray("egg_groups");
        for (int i = 0;i < eggGrp.length(); i++) {
            JSONObject entry = eggGrp.getJSONObject(i);
            eggGroupEntries.add(entry.getString("name"));
        }
        return eggGroupEntries;
    }
    public List<String> getPokemonEggGroups(JSONObject jsonObject) throws JSONException {
        List<String> pokemonOfEggGrp = new ArrayList<>();
        JSONArray eggGrp = jsonObject.getJSONArray("pokemon_species");
        for(int i = 0; i < eggGrp.length(); i++) {
            JSONObject pokeName = eggGrp.getJSONObject(i);
            pokemonOfEggGrp.add(pokeName.getString("name"));
        }
        return pokemonOfEggGrp;
    }

    /*
    public List<String> getEggGroup (String name,JSONObject jsonObject) {
        String url= "/pokemon-species/" + name;
        List<String> eggGroupEntries = new ArrayList<>();
        JSONArray eggGrp;
        try {
            eggGrp = requestGet(url).getJSONArray("egg_groups");
            for (int i = 0;i < eggGrp.length(); i++) {
                JSONObject entry = eggGrp.getJSONObject(i);
                eggGroupEntries.add(entry.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eggGroupEntries;
    }
     */


}
