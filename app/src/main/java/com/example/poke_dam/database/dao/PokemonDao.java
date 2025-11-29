package com.example.poke_dam.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.poke_dam.database.entidades.Pokemon;

import java.util.List;
@Dao
public interface PokemonDao {
    @Insert
    void insertPokemon(Pokemon pokemon);
    @Query("SELECT * FROM pokemons_capturados WHERE usuarioPropietario = :usuarioId ORDER BY nombre ASC")
    LiveData<List<Pokemon>> getPokemonsDeUsuario(String usuarioId);

    @Query("SELECT SUM(baseStat) FROM pokemons_capturados WHERE usuarioPropietario = :usuarioId")
    Integer getSumaPoderDeUsuario(String usuarioId);

    @Query("SELECT COUNT(*) FROM pokemons_capturados WHERE usuarioPropietario = :usuarioId AND nombre = :pokemonName")
    int countPokemonDeUsuario(String usuarioId, String pokemonName);
}
