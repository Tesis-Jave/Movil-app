package com.example.javepuntos.model
import com.google.gson.annotations.SerializedName

data class Cliente (
    @SerializedName("id_cliente") val id_cliente: Int,
    @SerializedName("nombrecliente") val nombrecliente: String,
    @SerializedName("nombrecomercial") val nombrecomercial: String,
    @SerializedName("cif") val cif: Long,
    @SerializedName("alias") val alias: String,
    @SerializedName("direccion1") val direccion1: String,
    @SerializedName("telefono1") val telefono1: Long,
    @SerializedName("fax") val fax: String,
    @SerializedName("faxpedidos") val faxpedidos: String,
    @SerializedName("e_mail") val e_mail: String,
    @SerializedName("digcontrolblanco") val digcontrolblanco: String,
    @SerializedName("codpostalbanco") val codpostalbanco: String,
    @SerializedName("nombrebanco") val nombrebanco: String,
    @SerializedName("poblacionbanco") val poblacionbanco: String,
    @SerializedName("fechanacimiento") val fechanacimiento: String,
    @SerializedName("sexo") val sexo: String,
    @SerializedName("nif20") val nif20: String,
    @SerializedName("descatalogado") val descatalogado: Int,
    @SerializedName("tipocliente") val tipocliente: Int
)