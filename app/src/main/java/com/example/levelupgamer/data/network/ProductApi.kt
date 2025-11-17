package com.example.levelupgamer.data.network

import com.example.levelupgamer.data.model.ProductDto
import retrofit2.http.GET

interface ProductApi {

    @GET("/api/productos")
    suspend fun getProductos(): List<ProductDto>
}