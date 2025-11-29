package com.example.poke_dam.ui.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide; // Asegúrate de tener Glide en build.gradle
import com.example.poke_dam.R;
import com.example.poke_dam.datos.Contenido;
import java.util.ArrayList;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.PokemonViewHolder> {

    private ArrayList<Contenido> listaPokemon;

    public ListaPokemonAdapter(ArrayList<Contenido> listaPokemon) {
        this.listaPokemon = listaPokemon;
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
        holder.txtNombre.setText(pokemon.getName());

        // Cargar imagen usando Glide
        Glide.with(holder.itemView.getContext())
                .load(pokemon.getSpriteUrl())
                .into(holder.imgSprite);
    }

    @Override
    public int getItemCount() {
        return listaPokemon.size();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSprite;
        TextView txtNombre;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSprite = itemView.findViewById(R.id.imgPokemon);
            txtNombre = itemView.findViewById(R.id.txtNombrePokemon);
        }
    }
}