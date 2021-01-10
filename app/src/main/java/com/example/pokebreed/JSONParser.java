package com.example.pokebreed;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    public static final String FILE_NAME = "pokemonHistory.json";

    // Alle Methoden kriegen ein jsonobject das direkt aus der API Request kommt und schreiben das jew. entsprechend zum Kontext um.

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

    public String getPicture(JSONObject jsonObject) throws JSONException {
        String pictureUrl;
        JSONObject pokemon = jsonObject.getJSONObject("sprites");
        pictureUrl = pokemon.getString("front_default");
        return pictureUrl;
    }
    public String getPictureItem(JSONObject jsonObject) throws JSONException {
        String pictureUrl;
        JSONObject pokemon = jsonObject.getJSONObject("sprites");
        pictureUrl = pokemon.getString("default");
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
            for(int i = 0;i < PokeEvo1.length();i++) {

                JSONObject pokeName = PokeEvo1.getJSONObject(i).getJSONObject("species");
                JSONArray temp = PokeEvo1.getJSONObject(i).getJSONArray("evolves_to");
                pokemonOfEvo.add(pokeName.getString("name"));
                for (int j = 0; j < temp.length(); j++) {
                    JSONObject pokename1 = temp.getJSONObject(j).getJSONObject("species");
                    pokemonOfEvo.add(pokename1.getString("name"));
                }
            }


            JSONObject pokenameBase =jsonObject.getJSONObject("chain").getJSONObject("species");

            pokemonOfEvo.add(pokenameBase.getString("name"));



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

    public JSONObject pokemonToJson(Pokemon pokemon) throws JSONException {
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject(gson.toJson(pokemon));
        return jsonObject;
    }

    public JSONObject createHistoryFile() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        jsonObject.putOpt("pokemonHistory",jsonArray);

        Log.e("createHistoryFile", jsonObject.toString());
        return jsonObject;
    }
    public List<Pokemon> getPokemonHistory (JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("pokemonHistory");
        List<Pokemon> pokemonHistory = new ArrayList<>();

        JsonParser parser = new JsonParser();
        Gson gson = new Gson();
        Log.e("jsonarry", String.valueOf(jsonArray.length()));
        for(int i = 0; i < jsonArray.length(); i++) {
            String mJsonString = jsonArray.getJSONObject(i).toString();
            JsonElement mJson =  parser.parse(mJsonString);
            Pokemon temp = gson.fromJson(mJson,Pokemon.class);
            pokemonHistory.add(temp);
            Log.e("for jp", String.valueOf(i));
        }
        return pokemonHistory;
    }

}
