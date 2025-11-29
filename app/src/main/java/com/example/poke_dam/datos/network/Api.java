package com.example.poke_dam.datos.network;

import com.example.poke_dam.datos.Respuesta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    @GET("pokemon/{id}")
    Call<Respuesta>  buscar(@Path("id") String id);
}
