package com.example.poke_dam.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.poke_dam.R;
import com.example.poke_dam.datos.Contenido;
import com.example.poke_dam.database.entidades.Pokemon;
import com.example.poke_dam.database.repositorio.PokemonRepositorio;
import com.example.poke_dam.ui.viewmodel.ListaPokemonAdapter;
import java.util.ArrayList;
import java.util.List;

public class ListaPokemonFragment extends Fragment {

    private ArrayList<Contenido> misPokemons = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayout layoutVacio;
    private ListaPokemonAdapter adapter;
    private PokemonRepositorio pokemonRepositorio;
    private String nombreUsuario;
    private TextView txtHeaderPoderTotal;

    public ListaPokemonFragment() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_pokemon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener el nombre de usuario desde los argumentos
        if (getArguments() != null) {
            nombreUsuario = getArguments().getString("nombreUsuario");
        }

        recyclerView = view.findViewById(R.id.recyclerViewPokemon);
        layoutVacio = view.findViewById(R.id.layoutVacio);
        txtHeaderPoderTotal = view.findViewById(R.id.txtHeaderPoderTotal);
        Button btnAtrapar = view.findViewById(R.id.btnAtrapar);
        Button btnSalir = view.findViewById(R.id.btnSalirLista);

        // Inicializar repositorio
        pokemonRepositorio = new PokemonRepositorio(requireActivity().getApplication());

        // Configurar RecyclerView (Grid de 2 columnas)
        adapter = new ListaPokemonAdapter(misPokemons, contenido -> {
            // Click listener: navegar al detalle del pokémon
            Bundle bundle = new Bundle();
            bundle.putInt("idPokedex", contenido.getIdPokedex());
            bundle.putString("nombre", contenido.getName());
            bundle.putInt("baseStat", contenido.getBaseStat());
            bundle.putString("spriteUrl", contenido.getSpriteUrl());
            bundle.putString("tipo", contenido.getType());
            bundle.putString("tipo2", contenido.getType2());

            Navigation.findNavController(view).navigate(R.id.action_listaPokemonFragment_to_detallePokemonFragment, bundle);
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        // Cargar pokémons del usuario desde la base de datos
        cargarPokemonsDeUsuario();

        actualizarVista();

        // Navegar a la pantalla de captura
        btnAtrapar.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("nombreUsuario", nombreUsuario);
            Navigation.findNavController(view).navigate(R.id.action_listaPokemonFragment_to_capturaFragment, bundle);
        });

        // Botón salir (volver al login)
        btnSalir.setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });

        // Escuchar si volvemos de capturar un pokemon
        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> {
            int idPokedex = bundle.getInt("idPokedex");
            String nombre = bundle.getString("nombre");
            String url = bundle.getString("url");
            int baseStat = bundle.getInt("base_stat");
            String tipo = bundle.getString("tipo", "normal");
            String tipo2 = bundle.getString("tipo2");

            // Crear el nuevo pokemon en la base de datos
            Pokemon nuevoPokemon = new Pokemon(idPokedex, nombre, baseStat, url, tipo, tipo2, nombreUsuario);
            pokemonRepositorio.insertarPokemon(nuevoPokemon);

            Toast.makeText(getContext(), "¡" + nombre + " capturado con éxito!", Toast.LENGTH_SHORT).show();
        });
    }

    private void cargarPokemonsDeUsuario() {
        if (nombreUsuario != null) {
            pokemonRepositorio.obtenerPokemonsDeUsuario(nombreUsuario).observe(getViewLifecycleOwner(), new Observer<List<Pokemon>>() {
                @Override
                public void onChanged(List<Pokemon> pokemons) {
                    misPokemons.clear();
                    for (Pokemon pokemon : pokemons) {
                        Contenido contenido = new Contenido(
                                pokemon.getIdPokedex(),
                                pokemon.getNombre(),
                                pokemon.getBaseStat(),
                                pokemon.getUrlSprite(),
                                pokemon.getTipo(),
                                pokemon.getTipo2()
                        );
                        misPokemons.add(contenido);
                    }
                    adapter.notifyDataSetChanged();
                    actualizarVista();
                    actualizarPoderTotal();
                }
            });
        }
    }

    private void actualizarPoderTotal() {
        if (nombreUsuario != null) {
            pokemonRepositorio.obtenerSumaPoderDeUsuario(nombreUsuario, sumaTotal -> {
                requireActivity().runOnUiThread(() -> {
                    txtHeaderPoderTotal.setText("PODER TOTAL: " + sumaTotal);
                });
            });
        }
    }

    private void actualizarVista() {
        if (misPokemons.isEmpty()) {
            layoutVacio.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            layoutVacio.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}