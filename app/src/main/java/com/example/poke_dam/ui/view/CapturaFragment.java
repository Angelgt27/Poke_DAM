package com.example.poke_dam.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.poke_dam.R;
import com.example.poke_dam.datos.Respuesta;
import com.example.poke_dam.datos.network.PokeAPI;
import com.example.poke_dam.database.repositorio.PokemonRepositorio;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CapturaFragment extends Fragment {

    private ImageView imgSprite;
    private TextView txtNombre;
    private TextView txtPoder;
    private Button btnCapturar;
    private Button btnSalir;

    private String pokemonNombreActual;
    private String pokemonUrlActual;
    private int pokemonStatBaseActual;
    private String pokemonTipoActual;
    private String pokemonTipo2Actual;
    private int pokemonIdPokedex;

    private String nombreUsuario;
    private PokemonRepositorio pokemonRepositorio;

    public CapturaFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_captura, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener el nombre de usuario desde los argumentos
        if (getArguments() != null) {
            nombreUsuario = getArguments().getString("nombreUsuario");
        }

        pokemonRepositorio = new PokemonRepositorio(requireActivity().getApplication());

        imgSprite = view.findViewById(R.id.imgSpriteCaptura);
        txtNombre = view.findViewById(R.id.txtNombreCaptura);
        txtPoder = view.findViewById(R.id.txtPoderCaptura);
        btnCapturar = view.findViewById(R.id.btnConfirmarCaptura);
        btnSalir = view.findViewById(R.id.btnSalirCaptura);

        buscarPokemonAleatorio();

        btnCapturar.setOnClickListener(v -> validarYCapturar(view));
        btnSalir.setOnClickListener(v -> Navigation.findNavController(view).popBackStack());
    }

    private void validarYCapturar(View view) {
        // Verificar si el pokémon ya está capturado
        pokemonRepositorio.verificarPokemonDuplicado(nombreUsuario, pokemonNombreActual, esDuplicado -> {
            if (esDuplicado) {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "¡Ya tienes este Pokémon capturado!", Toast.LENGTH_LONG).show();
                });
                return;
            }

            // Obtener la suma de poder del usuario
            pokemonRepositorio.obtenerSumaPoderDeUsuario(nombreUsuario, sumaTotal -> {
                requireActivity().runOnUiThread(() -> {
                    // Si el usuario no tiene pokémons (suma = 0), puede capturar cualquiera
                    if (sumaTotal == 0 || pokemonStatBaseActual <= sumaTotal) {
                        // Empaquetar los datos para devolverlos al fragmento anterior
                        Bundle result = new Bundle();
                        result.putInt("idPokedex", pokemonIdPokedex);
                        result.putString("nombre", pokemonNombreActual);
                        result.putString("url", pokemonUrlActual);
                        result.putInt("base_stat", pokemonStatBaseActual);
                        result.putString("tipo", pokemonTipoActual);
                        result.putString("tipo2", pokemonTipo2Actual);

                        getParentFragmentManager().setFragmentResult("requestKey", result);
                        Navigation.findNavController(view).popBackStack();
                    } else {
                        Toast.makeText(getContext(),
                                "¡Este Pokémon es demasiado fuerte! Poder: " + pokemonStatBaseActual +
                                        " / Tu poder total: " + sumaTotal,
                                Toast.LENGTH_LONG).show();
                    }
                });
            });
        });
    }

    private void buscarPokemonAleatorio() {
        Random random = new Random();
        int id = random.nextInt(1025) + 1;
        pokemonIdPokedex = id;

        // Llamada a la API
        PokeAPI.api.buscar(String.valueOf(id)).enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Respuesta datos = response.body();

                    pokemonNombreActual = datos.getName();
                    pokemonUrlActual = datos.getSprites().getFrontDefault();
                    pokemonStatBaseActual = datos.getStats().get(0).getBase_stat();

                    // Obtener los tipos del pokémon
                    if (datos.getTypes() != null && !datos.getTypes().isEmpty()) {
                        pokemonTipoActual = datos.getTypes().get(0).getType().getName();
                        // Segundo tipo si existe
                        if (datos.getTypes().size() > 1) {
                            pokemonTipo2Actual = datos.getTypes().get(1).getType().getName();
                        } else {
                            pokemonTipo2Actual = null;
                        }
                    } else {
                        pokemonTipoActual = "normal";
                        pokemonTipo2Actual = null;
                    }

                    // Actualizar UI
                    txtNombre.setText(pokemonNombreActual.toUpperCase());
                    txtPoder.setText("Poder Base: " + pokemonStatBaseActual);

                    Glide.with(requireContext())
                            .load(pokemonUrlActual)
                            .into(imgSprite);

                    // Habilitar botón ahora que tenemos datos
                    btnCapturar.setEnabled(true);
                } else {
                    txtNombre.setText("Error");
                    txtPoder.setText("Poder Base: --");
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                txtNombre.setText("Fallo de red");
                Log.e("PokeAPI", "Error: " + t.getMessage());
            }
        });
    }
}