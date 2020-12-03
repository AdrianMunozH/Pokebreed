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
