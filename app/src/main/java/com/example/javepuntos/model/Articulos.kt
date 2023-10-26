package com.example.javepuntos.model
import com.google.gson.annotations.SerializedName

data class Articulos(
    @SerializedName("id_articulo") val idArticulo: Int,
    @SerializedName("id_dpto") val idDpto: Int,
    @SerializedName("id_seccion") val idSeccion: Int,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("proveedor") val proveedor: String?,
    @SerializedName("unidadmedida") val unidadMedida: String?,
    @SerializedName("medidareferencia") val medidaReferencia: String?,
    @SerializedName("tipoarticulo") val tipoArticulo: String?,
    @SerializedName("stock") val stock: Int?,
    @SerializedName("precio") val precio: Float,
    @SerializedName("foto") val foto: String?
)
