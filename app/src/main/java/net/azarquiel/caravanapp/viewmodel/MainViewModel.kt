package net.azarquiel.caravanapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.caravanapp.api.MainRepository
import net.azarquiel.caravanapp.model.*

class MainViewModel : ViewModel() {

    private var repository: MainRepository = MainRepository()

    fun getComunidades(): MutableLiveData<List<Comunidad>> {
        val comunidades = MutableLiveData<List<Comunidad>>()
        GlobalScope.launch(Main) {
            comunidades.value = repository.getComunidades()
        }
        return comunidades
    }

    fun getProvinciasByComunidad(comunidad: Comunidad): MutableLiveData<List<Provincia>> {
        val provincias = MutableLiveData<List<Provincia>>()
        GlobalScope.launch(Main) {
            provincias.value = repository.getProvinciasByComunidad(comunidad)
        }
        return provincias
    }

    fun getMunicipiosByProvincia(provincia: Provincia): MutableLiveData<List<Municipio>> {
        val municipios = MutableLiveData<List<Municipio>>()
        GlobalScope.launch(Main) {
            municipios.value = repository.getMunicipiosByProvincia(provincia)
        }
        return municipios
    }

    fun getFotos(idlugar:String): MutableLiveData<List<Foto>> {
        val fotos = MutableLiveData<List<Foto>>()
        GlobalScope.launch(Main) {
            fotos.value = repository.getFotos(idlugar)
        }
        return fotos
    }

    fun getMediaPuntosByLugar(idlugar:String): MutableLiveData<String> {
        val avg = MutableLiveData<String>()
        GlobalScope.launch(Main) {
            avg.value = repository.getMediaPuntosByLugar(idlugar)
        }
        return avg
    }

    fun getLugares(latitud:String, longitud:String): MutableLiveData<List<Lugar>> {
        val lugares = MutableLiveData<List<Lugar>>()
        GlobalScope.launch(Main) {
            lugares.value = repository.getLugares(latitud, longitud )
        }
        return lugares
    }

    fun getUserByNickAndPass(usuario: Usuario):MutableLiveData<Usuario> {
        val usuarioresponse= MutableLiveData<Usuario>()
        GlobalScope.launch(Main) {
            usuarioresponse.value = repository.getUserByNickAndPass(usuario)
        }
        return usuarioresponse
    }

    fun saveUsuario(usuario: Usuario):MutableLiveData<Usuario> {
        val usuarioresponse= MutableLiveData<Usuario>()
        GlobalScope.launch(Main) {
            usuarioresponse.value = repository.saveUsuario(usuario)
        }
        return usuarioresponse
    }
    fun savePuntos(idlugar: String, punto: Punto):MutableLiveData<Punto> {
        val puntoresponse= MutableLiveData<Punto>()
        GlobalScope.launch(Main) {
            puntoresponse.value = repository.savePuntos(idlugar, punto)
        }
        return puntoresponse
    }
}

