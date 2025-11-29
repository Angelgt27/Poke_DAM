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
import android.widget.Toast;

import com.example.poke_dam.R;
import com.example.poke_dam.database.entidades.Usuario;
import com.example.poke_dam.database.repositorio.UsuarioRepositorio;

public class RegistrarUsuarioFragment extends Fragment {

    private EditText etUsuarioRegistro;
    private EditText etPasswordRegistro;
    private Button btnRegistrarUsuario;
    private UsuarioRepositorio repositorio;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registrar_usuario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        repositorio = new UsuarioRepositorio(requireActivity().getApplication());

        etUsuarioRegistro = view.findViewById(R.id.etUsuarioRegistro);
        etPasswordRegistro = view.findViewById(R.id.etPasswordRegistro);
        btnRegistrarUsuario = view.findViewById(R.id.btnRegistrarUsuario);

        btnRegistrarUsuario.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {
        String nombreUsuario = etUsuarioRegistro.getText().toString().trim();
        String password = etPasswordRegistro.getText().toString().trim();

        if (nombreUsuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }


        repositorio.verificarUsuarioExistente(nombreUsuario, existe -> {
            requireActivity().runOnUiThread(() -> {
                if (existe) {
                    Toast.makeText(getContext(), "El usuario '" + nombreUsuario + "' ya existe.", Toast.LENGTH_LONG).show();
                } else {
                    Usuario nuevoUsuario = new Usuario(nombreUsuario, password);
                    repositorio.insertar(nuevoUsuario);
                    Toast.makeText(getContext(), "Usuario registrado con éxito.", Toast.LENGTH_LONG).show();
                    navController.navigate(R.id.action_registrarUsuarioFragment_to_loginUsuarioFragment);
                }
            });
        });
    }
}