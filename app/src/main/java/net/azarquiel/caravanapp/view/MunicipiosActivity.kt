package net.azarquiel.caravanapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.caravanapp.R
import net.azarquiel.caravanapp.adapters.AdapterMunicipio
import net.azarquiel.caravanapp.databinding.ActivityMunicipiosBinding
import net.azarquiel.caravanapp.model.Municipio
import net.azarquiel.caravanapp.model.Provincia
import net.azarquiel.caravanapp.model.Usuario
import net.azarquiel.caravanapp.viewmodel.MainViewModel

class MunicipiosActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private var usuario: Usuario?=null
    private lateinit var municipios: List<Municipio>
    private lateinit var searchView: SearchView
    private lateinit var adapter: AdapterMunicipio
    private lateinit var viewModel: MainViewModel
    private lateinit var provincia: Provincia
    private var binding: ActivityMunicipiosBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMunicipiosBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        provincia = intent.getSerializableExtra("provincia") as Provincia
        usuario = intent.getSerializableExtra("usuario") as Usuario?
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initRV()
        getMunicipios(provincia)

    }

    private fun getMunicipios(provincia: Provincia) {
        viewModel.getMunicipiosByProvincia(provincia).observe(this, Observer { it ->
            it?.let{
                municipios = it
                adapter.setMunicipios(it)
            }
        })
    }

    private fun initRV() {
        adapter = AdapterMunicipio(this,R.layout.rowmunicipio)
        binding?.rvmunicipios?.adapter = adapter
        binding?.rvmunicipios?.layoutManager = LinearLayoutManager(this)
    }

    fun onClickMunicipio(v: View) {
        val municipio = v.tag as Municipio
        val intent = Intent(this, LugaresActivity::class.java)
        intent.putExtra("municipio", municipio)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_municipios, menu)
        // ************* <Filtro> ************
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search...")
        searchView.setOnQueryTextListener(this)
        // ************* </Filtro> ************

        return true
    }


    // ************* <Filtro> ************
    override fun onQueryTextChange(query: String): Boolean {
        val original = ArrayList<Municipio>(municipios)
        adapter.setMunicipios(original.filter { municipio -> municipio.nombre.toLowerCase().contains(query.toLowerCase()) })
        return false
    }

    override fun onQueryTextSubmit(text: String): Boolean {
        return false
    }
// ************* </Filtro> ************


}