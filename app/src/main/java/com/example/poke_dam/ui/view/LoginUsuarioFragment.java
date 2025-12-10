package com.example.poke_dam.ui.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poke_dam.R;
import com.example.poke_dam.database.entidades.Usuario;
import com.example.poke_dam.database.repositorio.UsuarioRepositorio;

public class LoginUsuarioFragment extends Fragment {

    private EditText etUsuarioLogin;
    private EditText etPasswordLogin;
    private Button btnLogin;
    private TextView tvCrearUsuario;
    private UsuarioRepositorio repositorio;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_usuario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        repositorio = new UsuarioRepositorio(requireActivity().getApplication());

        etUsuarioLogin = view.findViewById(R.id.etUsuarioLogin);
        etPasswordLogin = view.findViewById(R.id.etPasswordLogin);
        btnLogin = view.findViewById(R.id.btnLogin);
        tvCrearUsuario = view.findViewById(R.id.tvCrearUsuario);

        btnLogin.setOnClickListener(v -> intentarLogin());
        tvCrearUsuario.setOnClickListener(v -> navegarRegistro());
    }

    private void intentarLogin() {
        String user = etUsuarioLogin.getText().toString().trim();
        String pass = etPasswordLogin.getText().toString().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, ingrese usuario y contraseña.", Toast.LENGTH_SHORT).show();
            return;
        }

        repositorio.login(user, pass, new UsuarioRepositorio.LoginCallback() {
            @Override
            public void onResult(Usuario usuario) {
                requireActivity().runOnUiThread(() -> {
                    if (usuario != null) {
                        // Pasar el nombre de usuario al siguiente fragmento
                        Bundle bundle = new Bundle();
                        bundle.putString("nombreUsuario", usuario.getNombreUsuario());
                        navController.navigate(R.id.action_loginUsuarioFragment_to_listaPokemonFragment, bundle);
                    } else {
                        Toast.makeText(getContext(), "Usuario o contraseña incorrectos.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void navegarRegistro() {
        navController.navigate(R.id.action_loginUsuarioFragment_to_registrarUsuarioFragment);
    }
}