package com.example.poke_dam.database.entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios")
public class Usuario {

    @PrimaryKey
    @NonNull
    private String nombreUsuario;

    private String password;

    public Usuario(@NonNull String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }

    @NonNull
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(@NonNull String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
