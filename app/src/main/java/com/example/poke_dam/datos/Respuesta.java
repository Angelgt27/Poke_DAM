package com.example.poke_dam.datos;

import com.google.gson.annotations.SerializedName;

public class Respuesta {

    // El nombre del campo en el JSON es "name"
    @SerializedName("name")
    private String name;

    // El JSON tiene un objeto anidado llamado "sprites"
    @SerializedName("sprites")
    private Sprites sprites;

    // Getters
    public String getName() {
        return name;
    }

    public Sprites getSprites() {
        return sprites;
    }

    // Clase interna para mapear el objeto "sprites" del JSON
    public static class Sprites {

        // Dentro de sprites, queremos "front_default"
        @SerializedName("front_default")
        private String frontDefault;

        public String getFrontDefault() {
            return frontDefault;
        }
    }
}