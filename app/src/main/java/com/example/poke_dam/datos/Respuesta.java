package com.example.poke_dam.datos;

import com.google.gson.annotations.SerializedName;

public class Respuesta {

    @SerializedName("name")
    private String name;

    @SerializedName("sprites")
    private Sprites sprites;

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
}