package com.example.proyectoemergentes



import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("productos/Listar")
    fun getProductos(@Header("Authorization") token: String): Call<List<productos>>

    @PUT("productos/ModificarProducto/{id}")
    fun actualizarProducto(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body producto: productos
    ): Call<productos>

    @POST("productos/AgregarProducto")
    fun agregarProducto(
        @Header("Authorization") token: String,
        @Body producto: productos
    ): Call<productos>

    @DELETE("productos/EliminarProducto/{id}")
    fun eliminarProducto(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<Void>

    // Otros métodos según las necesidades de tu API
}