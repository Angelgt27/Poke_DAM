package com.example.poke_dam.datos;

public class Contenido {

    private int idPokedex;
    private String name;
    private int baseStat;
    private String spriteUrl;
    private String type;
    private String type2; // Segundo tipo (puede ser null)

    public Contenido(int idPokedex, String name, int baseStat, String spriteUrl, String type, String type2) {
        this.idPokedex = idPokedex;
        this.name = name;
        this.baseStat = baseStat;
        this.spriteUrl = spriteUrl;
        this.type = type;
        this.type2 = type2;
    }

    public int getIdPokedex() {
        return idPokedex;
    }

    public void setIdPokedex(int idPokedex) {
        this.idPokedex = idPokedex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(int baseStat) {
        this.baseStat = baseStat;
    }

    public String getSpriteUrl() {
        return spriteUrl;
    }

    public void setSpriteUrl(String spriteUrl) {
        this.spriteUrl = spriteUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }
}