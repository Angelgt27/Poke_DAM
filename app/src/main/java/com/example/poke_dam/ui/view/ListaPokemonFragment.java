package com.example.poke_dam.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.poke_dam.R;
import com.example.poke_dam.datos.Contenido;
import com.example.poke_dam.ui.viewmodel.ListaPokemonAdapter;
import java.util.ArrayList;

public class ListaPokemonFragment extends Fragment {

    private ArrayList<Contenido> misPokemons = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayout layoutVacio;
    private ListaPokemonAdapter adapter;

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

        recyclerView = view.findViewById(R.id.recyclerViewPokemon);
        layoutVacio = view.findViewById(R.id.layoutVacio);
        Button btnAtrapar = view.findViewById(R.id.btnAtrapar);

        // Configurar RecyclerView (Grid de 2 columnas)
        adapter = new ListaPokemonAdapter(misPokemons);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        actualizarVista();

        // Navegar a la pantalla de captura
        btnAtrapar.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_listaPokemonFragment_to_capturaFragment);
        });

        // Escuchar si volvemos de capturar un pokemon
        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> {
            String nombre = bundle.getString("nombre");
            String url = bundle.getString("url");

            // Crear el nuevo pokemon y añadirlo a la lista
            // (Ponemos stats y tipo genéricos por ahora, ya que Contenido los requiere)
            Contenido nuevoPokemon = new Contenido(nombre, 0, url, "normal");
            misPokemons.add(nuevoPokemon);

            adapter.notifyDataSetChanged();
            actualizarVista();
        });
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