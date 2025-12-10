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
    private int idPokedex;
    private String nombre;
    private int baseStat;
    private String urlSprite;
    private String tipo;
    private String tipo2; // Segundo tipo (puede ser null)
    private String usuarioPropietario;

    public Pokemon(int idPokedex, String nombre, int baseStat, String urlSprite, String tipo, String tipo2, String usuarioPropietario) {
        this.idPokedex = idPokedex;
        this.nombre = nombre;
        this.baseStat = baseStat;
        this.urlSprite = urlSprite;
        this.tipo = tipo;
        this.tipo2 = tipo2;
        this.usuarioPropietario = usuarioPropietario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPokedex() {
        return idPokedex;
    }

    public void setIdPokedex(int idPokedex) {
        this.idPokedex = idPokedex;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombrePokemon) {
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

    public String getTipo2() {
        return tipo2;
    }

    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }

    public String getUsuarioPropietario() {
        return usuarioPropietario;
    }

    public void setUsuarioPropietario(String usuarioPropietario) {
        this.usuarioPropietario = usuarioPropietario;
    }
}