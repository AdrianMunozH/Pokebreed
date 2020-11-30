package com.example.pokebreed;

import org.json.JSONObject;

public class JsonListener {
    private JSONObject json=null;
    private OnChangeListener listener;

    public JSONObject getJson(){
        return json;
    }

    public void setJson(JSONObject json){
        this.json=json;
        if(listener!=null) listener.onChange();
    }

    public OnChangeListener getListener(){
        return listener;
    }

    public void setListener(OnChangeListener listener){
        this.listener=listener;
    }

    public interface OnChangeListener{
        void onChange();
    }
}
