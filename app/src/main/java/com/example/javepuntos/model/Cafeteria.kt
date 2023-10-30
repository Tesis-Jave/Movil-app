package com.example.javepuntos.model

import com.google.gson.annotations.SerializedName

data class Cafeteria(
    @SerializedName("id_cafeteria") val idCafeteria: Int,
    @SerializedName("descripcion") val descripcion: String,
    val longitud: String?,
    val latitud: String?,
    val tipo: String?,
    @SerializedName("id_tarifa") val idTarifa: Int,
    @SerializedName("url") val url: String?
)
