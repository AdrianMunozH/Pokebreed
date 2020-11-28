package com.example.pokebreed;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class APIRequests extends  {
    final private String baseurl = "https://pokeapi.co/api/v2/";


    public List<String> getAllPoke() {
        List<String> allPoke= new ArrayList<>();
        try {
            JSONArray jsonArray = requestGet("pokemon?limit=800").getJSONArray("results");
            for (int i = 0; i < jsonArray.length();i++) {
                JSONObject pokemon = jsonArray.getJSONObject(i);
                String name = pokemon.getString("name");
                allPoke.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allPoke;
    }

    public JSONObject requestGet(String urlEnd) {
        RequestQueue requestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(0x7b, 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        RequestQueue queue = requestQueue;
        final JSONObject[] ret = new JSONObject[1];
        String url = baseurl.concat(urlEnd);

        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ret[0] = response;
                Log.e("Rest","rest erfolgreich");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error);
            }
        });
        queue.add(json);
        return ret[0];
    }
    
    public List<String> getEggGroup (String name) {
        String url= "/pokemon-species" + name;
        List<String> eggGroupEntries = new ArrayList<>();
        JSONArray egggrp = null;
        try {
            egggrp = requestGet(url).getJSONArray("egg_groups");
            for (int i = 0;i < egggrp.length(); i++) {
                JSONObject entry = egggrp.getJSONObject(i);
                eggGroupEntries.add(entry.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eggGroupEntries;
    }
}
