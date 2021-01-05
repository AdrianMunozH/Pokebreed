package com.example.pokebreed;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Pokemon implements Serializable {
    private int id;
    private String name;
    private String ability;
    private String nature;
    private String moves;
    private List<String> eggGroups = new ArrayList<>();
    private List<String> types=new ArrayList<>();

    //DVs(Stats) 0-31
    private String kp;
    private String attack;
    private String defense;
    private String specialAttack;
    private String specialDefense;
    private String speed;

    //evolutionchain url
    private String evoUrl;

    //Parents
    private Pokemon mother;
    private Pokemon father;

    //Item
    private String Item;

    //others
    boolean calculateStats;

    //Constructors

    public Pokemon(String name){
        this.name=name;
    }



    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public void setEggGroups(List<String> eggGroups) {
        this.eggGroups = eggGroups;
    }

    public void setMoves(String moves) {
        this.moves = moves;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void setKp(String kp) {
        this.kp = kp;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public void setSpecialAttack(String specialAttack) {
        this.specialAttack = specialAttack;
    }

    public void setSpecialDefense(String specialDefense) {
        this.specialDefense = specialDefense;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public void setCalculateStats(boolean calculateStats) {
        this.calculateStats = calculateStats;
    }

    public void setEvoUrl(String evoUrl) { this.evoUrl = evoUrl; }

    public void setItem(String item) {
        Item = item;
    }

    public void setMother(Pokemon mother) {
        this.mother = mother;
    }

    public void setFather(Pokemon father) {
        this.father = father;
    }

    //Getters


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbility() {
        return ability;
    }

    public String getNature() {
        return nature;
    }

    public List<String> getEggGroups() {
        return eggGroups;
    }

    public String getMoves() {
        return moves;
    }

    public List<String> getTypes() {
        return types;
    }

    public String getKp() {
        return kp;
    }

    public String getAttack() {
        return attack;
    }

    public String getDefense() {
        return defense;
    }

    public String getSpecialAttack() {
        return specialAttack;
    }

    public String getSpecialDefense() {
        return specialDefense;
    }

    public String getSpeed() {
        return speed;
    }

    public boolean isCalculateStats() {
        return calculateStats;
    }

    public String getEvoUrl() { return evoUrl; }

    public String getItem() {
        return Item;
    }

    public Pokemon getMother() {
        return mother;
    }

    public Pokemon getFather() {
        return father;
    }
}
