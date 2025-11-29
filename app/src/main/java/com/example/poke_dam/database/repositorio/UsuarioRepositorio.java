package com.example.poke_dam.database.repositorio;

import android.app.Application;

import com.example.poke_dam.database.UsuariosPokemonsDB;
import com.example.poke_dam.database.dao.UsuarioDao;
import com.example.poke_dam.database.entidades.Usuario;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UsuarioRepositorio {

    private final UsuarioDao usuarioDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public UsuarioRepositorio(Application application) {
        UsuariosPokemonsDB db = UsuariosPokemonsDB.getDatabase(application);
        this.usuarioDao = db.UsuarioDao();
    }

    public void insertar(Usuario usuario) {
        executor.execute(() -> {
            usuarioDao.insertUsuario(usuario);
        });
    }

    public void login(String user, String pass, LoginCallback callback) {
        executor.execute(() -> {
            Usuario usuario = usuarioDao.loginUsuario(user, pass);
            if (callback != null) {
                callback.onResult(usuario);
            }
        });
    }

    public void verificarUsuarioExistente(String user, UsuarioExistenteCallback callback) {
        executor.execute(() -> {
            Usuario usuario = usuarioDao.getUsuarioByNombre(user);
            if (callback != null) {
                callback.onResult(usuario != null);
            }
        });
    }
    public interface LoginCallback {
        void onResult(Usuario usuario);
    }

    public interface UsuarioExistenteCallback {
        void onResult(boolean existe);
    }
}
