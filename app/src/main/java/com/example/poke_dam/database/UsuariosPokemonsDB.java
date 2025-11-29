package com.example.poke_dam.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.poke_dam.database.dao.PokemonDao;
import com.example.poke_dam.database.dao.UsuarioDao;
import com.example.poke_dam.database.entidades.Pokemon;
import com.example.poke_dam.database.entidades.Usuario;

@Database(entities = {Usuario.class, Pokemon.class}, version = 1, exportSchema = false)
public abstract class UsuariosPokemonsDB extends RoomDatabase {

    public abstract UsuarioDao UsuarioDao();
    public abstract PokemonDao PokemonDao();

    private static volatile UsuariosPokemonsDB INSTANCE;
    private static final String DATABASE_NAME = "poke_dam_db";

    public static UsuariosPokemonsDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UsuariosPokemonsDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UsuariosPokemonsDB.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
