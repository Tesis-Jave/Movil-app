package com.example.javepuntos.model

import com.google.gson.annotations.SerializedName

data class Departamento(
    @SerializedName("id_dpto") val idDpto: Int,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("url") val url: String?
)
