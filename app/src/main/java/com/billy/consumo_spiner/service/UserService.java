package com.billy.consumo_spiner.service;

import com.billy.consumo_spiner.entity.UserCharla;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

    @GET("verUsuariosProfesionales")
    public abstract Call<List<UserCharla>> listaProfesionales();

}
