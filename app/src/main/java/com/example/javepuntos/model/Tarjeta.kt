package com.example.javepuntos.model
import com.google.gson.annotations.SerializedName

data class Tarjeta (
    @SerializedName("id_tarjeta") val idtarjeta: Int,
    @SerializedName("id_cliente") val idcliente: Int,
    @SerializedName("posicion") val posicion: Int,
    @SerializedName("id_tipotarjeta") val idtipotarjeta: Int,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("valida") val valida: Int,
    @SerializedName("saldotarjeta") val saldotarjeta: Double,
    @SerializedName("entregada") val entregada: String,
    @SerializedName("observaciones") val observaciones: String,
    @SerializedName("alias") val alias: String,
    @SerializedName("codmoneda") val codmoneda: String
)