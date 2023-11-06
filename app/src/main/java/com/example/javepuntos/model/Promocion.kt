package com.example.javepuntos.model

import com.google.gson.annotations.SerializedName
data class Promociones(
    @SerializedName("id_promocion") val idPromocion: Int?,
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName("fechainicio") val fechainicio: String?,
    @SerializedName("fechafin") val fechafin: String?
)