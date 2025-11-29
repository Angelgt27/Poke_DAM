package com.example.poke_dam.database.entidades;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "pokemons_capturados",
        foreignKeys = @ForeignKey(entity = Usuario.class,
                parentColumns = "nombreUsuario",
                childColumns = "usuarioPropietario",
                onDelete = ForeignKey.CASCADE))
public class Pokemon implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private int baseStat; // Campo para el poder base (base_stat)
    private String urlSprite;
    private String tipo;
    private String usuarioPropietario;

    public Pokemon(String nombre, int baseStat, String urlSprite, String tipo, String usuarioPropietario) {
        this.nombre = nombre;
        this.baseStat = baseStat;
        this.urlSprite = urlSprite;
        this.tipo = tipo;
        this.usuarioPropietario = usuarioPropietario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombrePokemon() {
        return nombre;
    }

    public void setNombrePokemon(String nombrePokemon) {
        this.nombre = nombrePokemon;
    }

    public int getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(int baseStat) {
        this.baseStat = baseStat;
    }

    public String getUrlSprite() {
        return urlSprite;
    }

    public void setUrlSprite(String urlSprite) {
        this.urlSprite = urlSprite;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUsuarioPropietario() {
        return usuarioPropietario;
    }

    public void setUsuarioPropietario(String usuarioPropietario) {
        this.usuarioPropietario = usuarioPropietario;
    }
}
