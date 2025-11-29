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

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CapturaFragment extends Fragment {

    private ImageView imgSprite;
    private TextView txtNombre;
    private Button btnCapturar;

    private String pokemonNombreActual;
    private String pokemonUrlActual;

    private int pokemonStatBaseActual;

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

        imgSprite = view.findViewById(R.id.imgSpriteCaptura);
        txtNombre = view.findViewById(R.id.txtNombreCaptura);
        btnCapturar = view.findViewById(R.id.btnConfirmarCaptura);

        buscarPokemonAleatorio();

        btnCapturar.setOnClickListener(v -> {
            // Empaquetar los datos para devolverlos al fragmento anterior
            Bundle result = new Bundle();
            result.putString("nombre", pokemonNombreActual);
            result.putString("url", pokemonUrlActual);
            result.putInt("base_stat", pokemonStatBaseActual);

            // "requestKey" debe coincidir con el que pusimos en ListaPokemonFragment
            getParentFragmentManager().setFragmentResult("requestKey", result);

            // Volver atrás
            Navigation.findNavController(view).popBackStack();
        });
    }

    private void buscarPokemonAleatorio() {
        Random random = new Random();
        int id = random.nextInt(1025) + 1;

        // Llamada a la API
        PokeAPI.api.buscar(String.valueOf(id)).enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Respuesta datos = response.body();

                    pokemonNombreActual = datos.getName();
                    pokemonUrlActual = datos.getSprites().getFrontDefault();
                    pokemonStatBaseActual = datos.getStats().get(0).getBase_stat();


                    // Actualizar UI
                    txtNombre.setText(pokemonNombreActual);

                    Glide.with(requireContext())
                            .load(pokemonUrlActual)
                            .into(imgSprite);

                    // Habilitar botón ahora que tenemos datos
                    btnCapturar.setEnabled(true);
                } else {
                    txtNombre.setText("Error");
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