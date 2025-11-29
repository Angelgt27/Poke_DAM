package com.example.poke_dam.datos;

public class Contenido {

    private String name;
    private int baseStat;
    private String spriteUrl;
    private String type;

    public Contenido(String name, int baseStat, String spriteUrl, String type) {
        this.name = name;
        this.baseStat = baseStat;
        this.spriteUrl = spriteUrl;
        this.type = type;
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
}
