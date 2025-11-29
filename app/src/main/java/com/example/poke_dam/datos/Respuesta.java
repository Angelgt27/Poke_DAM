package com.example.poke_dam.datos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Respuesta {

    @SerializedName("name")
    private String name;

    @SerializedName("sprites")
    private Sprites sprites;

    @SerializedName("stats")
    private List<Stats> stats;

    public List<Stats> getStats() {
        return stats;
    }

    public String getName() {
        return name;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public static class Sprites {
        // Dentro de sprites, queremos "front_default" que es la imagen del pokemon de frente
        @SerializedName("front_default")
        private String frontDefault;

        public String getFrontDefault() {
            return frontDefault;
        }
    }

    public static class Stats {
        @SerializedName("base_stat")
        private int base_stat;

        public int getBase_stat() {
            return base_stat;
        }
    }
}