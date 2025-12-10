package com.example.poke_dam.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.poke_dam.R;

public class DetallePokemonFragment extends Fragment {

    private ImageView imgSprite;
    private TextView txtIdPokedex;
    private TextView txtNombre;
    private TextView txtBaseStat;
    private TextView txtTipo;
    private android.widget.Button btnSalir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalle_pokemon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgSprite = view.findViewById(R.id.imgSpriteDetalle);
        txtIdPokedex = view.findViewById(R.id.txtIdPokedex);
        txtNombre = view.findViewById(R.id.txtNombreDetalle);
        txtBaseStat = view.findViewById(R.id.txtBaseStatDetalle);
        txtTipo = view.findViewById(R.id.txtTipoDetalle);
        btnSalir = view.findViewById(R.id.btnSalirDetalle);

        btnSalir.setOnClickListener(v -> Navigation.findNavController(view).popBackStack());

        // Obtener datos del bundle
        if (getArguments() != null) {
            int idPokedex = getArguments().getInt("idPokedex");
            String nombre = getArguments().getString("nombre");
            int baseStat = getArguments().getInt("baseStat");
            String spriteUrl = getArguments().getString("spriteUrl");
            String tipo = getArguments().getString("tipo");
            String tipo2 = getArguments().getString("tipo2");

            // Mostrar datos
            txtIdPokedex.setText("Nº " + String.format("%03d", idPokedex));
            txtNombre.setText(nombre.toUpperCase());
            txtBaseStat.setText("Poder Base: " + baseStat);

            // Mostrar tipos
            if (tipo2 != null && !tipo2.isEmpty()) {
                txtTipo.setText("Tipos: " + tipo.toUpperCase() + " / " + tipo2.toUpperCase());
            } else {
                txtTipo.setText("Tipo: " + tipo.toUpperCase());
            }

            Glide.with(requireContext())
                    .load(spriteUrl)
                    .into(imgSprite);
        }
    }
}