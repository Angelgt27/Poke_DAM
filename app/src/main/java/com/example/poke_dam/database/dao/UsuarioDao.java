package com.example.poke_dam.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.poke_dam.database.entidades.Usuario;

@Dao
public interface UsuarioDao {

    @Insert
    void insertUsuario(Usuario usuario);
    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :user AND password = :pass LIMIT 1")
    Usuario loginUsuario(String user, String pass);

    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :user LIMIT 1")
    Usuario getUsuarioByNombre(String user);


}
