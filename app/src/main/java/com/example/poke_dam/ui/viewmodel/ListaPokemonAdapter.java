package com.example.poke_dam.ui.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.poke_dam.R;
import com.example.poke_dam.datos.Contenido;
import java.util.ArrayList;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.PokemonViewHolder> {

    private ArrayList<Contenido> listaPokemon;
    private OnPokemonClickListener listener;

    public interface OnPokemonClickListener {
        void onPokemonClick(Contenido contenido);
    }

    public ListaPokemonAdapter(ArrayList<Contenido> listaPokemon, OnPokemonClickListener listener) {
        this.listaPokemon = listaPokemon;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_listapokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Contenido pokemon = listaPokemon.get(position);
        holder.txtIdPokedex.setText("#" + String.format("%03d", pokemon.getIdPokedex()));
        holder.txtNombre.setText(pokemon.getName());
        holder.txtPoder.setText("Poder: " + pokemon.getBaseStat());

        // Cargar imagen usando Glide
        Glide.with(holder.itemView.getContext())
                .load(pokemon.getSpriteUrl())
                .into(holder.imgSprite);

        // Manejar click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPokemonClick(pokemon);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPokemon.size();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSprite;
        TextView txtIdPokedex;
        TextView txtNombre;
        TextView txtPoder;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSprite = itemView.findViewById(R.id.imgPokemon);
            txtIdPokedex = itemView.findViewById(R.id.txtIdPokedex);
            txtNombre = itemView.findViewById(R.id.txtNombrePokemon);
            txtPoder = itemView.findViewById(R.id.txtPoder);
        }
    }
}