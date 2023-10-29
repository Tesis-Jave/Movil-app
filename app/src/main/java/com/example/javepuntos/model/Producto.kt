package com.example.javepuntos.model

import com.google.gson.annotations.SerializedName

data class Producto (
    @SerializedName ("dto")val dto: String,
    @SerializedName ("id_articulo")val id_articulo: Int,
    @SerializedName ("id_preciosventa")val id_preciosventa: Int,
    @SerializedName ("id_tarifa")val id_tarifa: Int,
    @SerializedName ("preciobruto")val preciobruto: Double,
    @SerializedName ("precioneto")val precioneto: Double)