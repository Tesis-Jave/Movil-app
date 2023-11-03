package com.example.javepuntos.model

import com.google.gson.annotations.SerializedName

data class Perfil (
    @SerializedName("id_perfil") val id_perfil: Int,
    @SerializedName("usuario") val usuario: String,
    @SerializedName("password") val password: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("cedula") val cedula: Long,
    @SerializedName("cargo") val cargo: String,
    @SerializedName("admin") val admin: Boolean,
)


