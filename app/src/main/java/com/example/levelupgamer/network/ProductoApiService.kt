package com.example.levelupgamer.network

import com.example.levelupgamer.data.model.Producto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductoApiService {
    @GET("api/productos")
    suspend fun getProductos(): List<Producto>
    @POST("api/productos")
    suspend fun createProducto(@Body producto: Producto): Producto
}