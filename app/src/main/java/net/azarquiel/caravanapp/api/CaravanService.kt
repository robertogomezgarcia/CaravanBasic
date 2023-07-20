package net.azarquiel.caravanapp.api

import kotlinx.coroutines.Deferred
import net.azarquiel.caravanapp.model.Punto
import net.azarquiel.caravanapp.model.Respuesta
import net.azarquiel.caravanapp.model.Usuario
import retrofit2.Response
import retrofit2.http.*


interface CaravanService {
    // No necesita nada para trabajar
    @GET("comunidad")
    fun getComunidades(): Deferred<Response<Respuesta>>

    // idcomunidad en la url
    @GET("comunidad/{idcomunidad}/provincia")
    fun getProvinciasByComunidad(@Path("idcomunidad") idcomunidad: String): Deferred<Response<Respuesta>>

    // idcomunidad en la url
    @GET("provincia/{idprovincia}/municipio")
    fun getMunicipiosByProvincia(@Path("idprovincia") idprovincia: String): Deferred<Response<Respuesta>>

    @GET("lugar/{idlugar}/fotos")
    fun getFotos(@Path("idlugar") idlugar: String): Deferred<Response<Respuesta>>

    @GET("lugar/{idlugar}/avgpuntos")
    fun getMediaPuntosByLugar(@Path("idlugar") idlugar: String): Deferred<Response<Respuesta>>

    // nick y pass como variables en la url?nick=paco&pass=paco
    @GET("usuario")
    fun getUserByNickAndPass(
        @Query("nick") nick: String,
        @Query("pass") pass: String): Deferred<Response<Respuesta>>

    @GET("lugar")
    fun getLugares(
        @Query("latitud") latitud: String,
        @Query("longitud") longitud: String): Deferred<Response<Respuesta>>

    // post con objeto en json
    @POST("usuario")
    fun saveUsuario(@Body usuario: Usuario): Deferred<Response<Respuesta>>

    @POST("lugar/{idlugar}/puntos")
    fun savePuntos(@Path("idlugar") idlugar: String,
                   @Body punto: Punto): Deferred<Response<Respuesta>>

// ……..   resto de métodos de la interfaz ………..

}