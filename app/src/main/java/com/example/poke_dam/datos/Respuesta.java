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

    @SerializedName("types")
    private List<TypeSlot> types;

    public List<Stats> getStats() {
        return stats;
    }

    public String getName() {
        return name;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public List<TypeSlot> getTypes() {
        return types;
    }

    public static class Sprites {
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

    public static class TypeSlot {
        @SerializedName("type")
        private Type type;

        public Type getType() {
            return type;
        }
    }

    public static class Type {
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }
    }
}