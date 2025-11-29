package com.example.poke_dam.database.repositorio;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.poke_dam.database.UsuariosPokemonsDB;
import com.example.poke_dam.database.dao.PokemonDao;
import com.example.poke_dam.database.entidades.Pokemon;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PokemonRepositorio {
    private final PokemonDao pokemonDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public PokemonRepositorio(Application application) {
        UsuariosPokemonsDB db = UsuariosPokemonsDB.getDatabase(application);
        this.pokemonDao = db.PokemonDao();
    }

    public LiveData<List<Pokemon>> obtenerPokemonsDeUsuario(String usuarioId) {
        return pokemonDao.getPokemonsDeUsuario(usuarioId);
    }

    public void insertarPokemon(Pokemon pokemon) {
        executor.execute(() -> {
            pokemonDao.insertPokemon(pokemon);
        });
    }

    public void obtenerSumaPoderDeUsuario(String usuarioId, SumaPoderCallback callback) {
        executor.execute(() -> {
            Integer suma = pokemonDao.getSumaPoderDeUsuario(usuarioId);
            if (callback != null) {
                callback.onResult(suma != null ? suma : 0);
            }
        });
    }

    public void verificarPokemonDuplicado(String usuarioId, String pokemonName, DuplicadoCallback callback) {
        executor.execute(() -> {
            int count = pokemonDao.countPokemonDeUsuario(usuarioId, pokemonName);
            if (callback != null) {
                callback.onResult(count > 0);
            }
        });
    }

    public interface SumaPoderCallback {
        void onResult(int sumaTotal);
    }

    public interface DuplicadoCallback {
        void onResult(boolean esDuplicado);
    }
}
