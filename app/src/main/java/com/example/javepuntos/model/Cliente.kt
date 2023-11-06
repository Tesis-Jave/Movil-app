package com.example.javepuntos.model
import com.google.gson.annotations.SerializedName

data class Cliente(
    @SerializedName("id_cliente") var id_cliente: Int,
    @SerializedName("nombrecliente") var nombrecliente: String,
    @SerializedName("nombrecomercial") var nombrecomercial: String?,
    @SerializedName("cif") var cif: Long,
    @SerializedName("alias") var alias: String?,
    @SerializedName("direccion1") var direccion1: String,
    @SerializedName("telefono1") var telefono1: Long,
    @SerializedName("fax") var fax: String?,
    @SerializedName("faxpedidos") var faxpedidos: String?,
    @SerializedName("e_mail") var e_mail: String,
    @SerializedName("digcontrolblanco") var digcontrolblanco: String?,
    @SerializedName("codpostalbanco") var codpostalbanco: String?,
    @SerializedName("nombrebanco") var nombrebanco: String?,
    @SerializedName("poblacionbanco") var poblacionbanco: String?,
    @SerializedName("fechanacimiento") var fechanacimiento: String,
    @SerializedName("sexo") var sexo: String?,
    @SerializedName("nif20") var nif20: String?,
    @SerializedName("descatalogado") var descatalogado: Int?,
    @SerializedName("tipocliente") var tipocliente: Int?
)