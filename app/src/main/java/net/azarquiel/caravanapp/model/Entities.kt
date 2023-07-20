package net.azarquiel.caravanapp.model

import java.io.Serializable

data class Comunidad(var id:String="", var nombre:String=""):Serializable

data class Provincia(var id:String="", var comunidad:String="", var nombre:String=""):Serializable
data class Usuario(var id:String="", var nick:String="", var pass:String=""):Serializable
data class Municipio (var id:String="", var nombre:String="", var provincia:String="", var latitud:String="", var longitud:String=""):Serializable
data class Punto(var id:String="", var usuario:String="", var lugar:String="", var puntos:String="")
data class Lugar(var id:String="", var titre:String="", var description_es:String=""):Serializable
data class Foto(var link_large:String="")

data class Respuesta(
    var comunidades:List<Comunidad>,
    var provincias:List<Provincia>,
    var municipios:List<Municipio>,
    var p4n_photos:List<Foto>,
    var lieux:List<Lugar>,
    var usuario:Usuario,
    var punto:Punto,
    var avg:String
)