package com.example.pokebreed;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    public static final String FILE_NAME = "pokemonHistory.json";

    private List<Pokemon> pokemonHistory = new ArrayList<>();

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
    public List<String> getBaseStats(JSONObject jsonObject) throws JSONException {
        List<String> baseStats = new ArrayList<>();
        JSONArray bstats = jsonObject.getJSONArray("stats");
        for (int i = 0; i < bstats.length() ; i++) {
            JSONObject stat = bstats.getJSONObject(i);
            baseStats.add(stat.getString("base_stat"));
        }
        return baseStats;
    }
    public List<String> getType(JSONObject jsonObject) throws JSONException {
        List<String> types = new ArrayList<>();
        JSONArray arrTypes = jsonObject.getJSONArray("types");
        for (int i = 0; i < arrTypes.length() ; i++) {
            JSONObject type = arrTypes.getJSONObject(i).getJSONObject("type");
            types.add(type.getString("name"));
        }
        return types;
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

    public List<String> getSpeciesEggGroups(JSONObject jsonObject) throws JSONException {
        List<String> eggGroupEntries = new ArrayList<>();
        JSONArray eggGrp = jsonObject.getJSONArray("egg_groups");
        for (int i = 0;i < eggGrp.length(); i++) {
            JSONObject entry = eggGrp.getJSONObject(i);
            eggGroupEntries.add(entry.getString("name"));
        }
        Log.e( "getSpeciesEggGroups: ",eggGroupEntries.get(0) );
        return eggGroupEntries;
    }
    public List<String> getPokemonOfEggGroup(JSONObject jsonObject) throws JSONException {
        List<String> pokemonOfEggGrp = new ArrayList<>();
        JSONArray eggGrp = jsonObject.getJSONArray("pokemon_species");
        for(int i = 0; i < eggGrp.length(); i++) {
            JSONObject pokeName = eggGrp.getJSONObject(i);
            pokemonOfEggGrp.add(pokeName.getString("name"));
        }
        return pokemonOfEggGrp;
    }

    public List<String> getEvoPokemon(JSONObject jsonObject) throws JSONException {
        List<String> pokemonOfEvo = new ArrayList<>();
        JSONArray PokeEvo1 = jsonObject.getJSONObject("chain").getJSONArray("evolves_to");
            JSONObject pokeName = PokeEvo1.getJSONObject(0).getJSONObject("species");
            JSONObject pokename1= PokeEvo1.getJSONObject(0).getJSONArray("evolves_to").getJSONObject(0).getJSONObject("species");

            pokemonOfEvo.add(pokeName.getString("name"));
            pokemonOfEvo.add(pokename1.getString("name"));

        return pokemonOfEvo;
    }



    public String getItem(JSONObject jsonObject) throws JSONException {
        String item;

        item = jsonObject.getString("name");
        return item;
    }

    public String getEvolutionChainUrl(JSONObject jsonObject) throws JSONException {
        String url;

        url = jsonObject.getJSONObject("evolution_chain").getString("url");
        Log.e( "getEvolutionChainUrl: ",url.substring(26) );
        return url.substring(26) ;
    }


    // History

    public void addPokemonToHistory(Pokemon pokemon) {
        //saveData(pokemonToJson(pokemon));

        Log.e("pokemon json",pokemonToJson(pokemon));
    }
    public String pokemonToJson(Pokemon pokemon) {
        Gson gson = new Gson();
        return gson.toJson(pokemon);
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
