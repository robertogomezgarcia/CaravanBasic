package net.azarquiel.caravanapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.caravanapp.R
import net.azarquiel.caravanapp.adapters.AdapterProvincia
import net.azarquiel.caravanapp.databinding.ActivityProvinciasBinding
import net.azarquiel.caravanapp.model.Comunidad
import net.azarquiel.caravanapp.model.Provincia
import net.azarquiel.caravanapp.model.Usuario
import net.azarquiel.caravanapp.viewmodel.MainViewModel

class ProvinciasActivity : AppCompatActivity() {

    private var usuario: Usuario?=null
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: AdapterProvincia
    private lateinit var comunidad: Comunidad
    private var binding: ActivityProvinciasBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProvinciasBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        comunidad = intent.getSerializableExtra("comunidad") as Comunidad
        usuario = intent.getSerializableExtra("usuario") as Usuario?
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initRV()
        getProvincias(comunidad)

    }

    private fun getProvincias(comunidad: Comunidad) {
        viewModel.getProvinciasByComunidad(comunidad).observe(this, Observer { it ->
            it?.let{
                adapter.setProvincias(it)
            }
        })
    }

    private fun initRV() {
        adapter = AdapterProvincia(this,R.layout.rowprovincia)
        binding?.rvprovincias?.adapter = adapter
        binding?.rvprovincias?.layoutManager = LinearLayoutManager(this)
    }

    fun onClickProvincia(v: View) {
        val provincia = v.tag as Provincia
        val intent = Intent(this, MunicipiosActivity::class.java)
        intent.putExtra("provincia", provincia)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }
}