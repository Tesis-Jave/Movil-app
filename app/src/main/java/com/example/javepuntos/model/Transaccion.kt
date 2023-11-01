package com.example.javepuntos.model
import com.google.gson.annotations.SerializedName
import java.util.Date

data class Transaccion(
    @SerializedName("id_transaccion") val idtransaccion: Int,
    @SerializedName("id_tarjeta") val idtarjeta: Int,
    @SerializedName("id_cafeteria") val idcafeteria: Int,
    @SerializedName("puntosredimidos") val puntos_redimidos: Int,
    @SerializedName("fecha") val fecha: Date,
    @SerializedName("descripcion") val descripcion: String
)
