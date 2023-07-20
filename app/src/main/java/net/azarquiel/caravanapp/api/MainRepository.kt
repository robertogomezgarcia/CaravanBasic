package net.azarquiel.caravanapp.api

import net.azarquiel.caravanapp.model.*

class MainRepository() {
    val service = WebAccess.caravanService

    suspend fun getComunidades(): List<Comunidad> {
        val webResponse = service.getComunidades().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.comunidades
        }
        return emptyList()
    }

    suspend fun getProvinciasByComunidad(comunidad: Comunidad): List<Provincia> {
        val webResponse = service.getProvinciasByComunidad(comunidad.id).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.provincias
        }
        return emptyList()
    }

    suspend fun getFotos(idlugar:String): List<Foto> {
        val webResponse = service.getFotos(idlugar).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.p4n_photos
        }
        return emptyList()
    }

    suspend fun getMediaPuntosByLugar(idlugar:String): String{
        val webResponse = service.getMediaPuntosByLugar(idlugar).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.avg
        }
        return ""
    }

    suspend fun getMunicipiosByProvincia(provincia: Provincia): List<Municipio> {
        val webResponse = service.getMunicipiosByProvincia(provincia.id).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.municipios
        }
        return emptyList()
    }

    suspend fun getUserByNickAndPass(usuario: Usuario): Usuario? {
        var usuarioresponse:Usuario? = null
        val webResponse = service.getUserByNickAndPass(usuario.nick,usuario.pass).await()
        if (webResponse.isSuccessful) {
            usuarioresponse = webResponse.body()!!.usuario
        }
        return usuarioresponse
    }
    suspend fun getLugares(latitud:String, longitud:String): List<Lugar> {
        val webResponse = service.getLugares(latitud,longitud).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.lieux
        }
        return emptyList()
    }

    suspend fun saveUsuario(usuario: Usuario): Usuario? {
        var usuarioresponse:Usuario? = null
        val webResponse = service.saveUsuario(usuario).await()
        if (webResponse.isSuccessful) {
            usuarioresponse = webResponse.body()!!.usuario
        }
        return usuarioresponse
    }

    suspend fun savePuntos(idlugar: String, punto: Punto): Punto? {
        var puntoresponse:Punto? = null
        val webResponse = service.savePuntos(idlugar, punto).await()
        if (webResponse.isSuccessful) {
            puntoresponse = webResponse.body()!!.punto
        }
        return puntoresponse
    }

}
