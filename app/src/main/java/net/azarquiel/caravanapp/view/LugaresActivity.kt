package net.azarquiel.caravanapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import net.azarquiel.caravanapp.R
import net.azarquiel.caravanapp.adapters.AdapterLugar
import net.azarquiel.caravanapp.databinding.ActivityLugaresBinding
import net.azarquiel.caravanapp.model.*
import net.azarquiel.caravanapp.viewmodel.MainViewModel

class LugaresActivity : AppCompatActivity() {
    private var usuario: Usuario?=null
    private lateinit var adapter: AdapterLugar
    private lateinit var viewModel: MainViewModel
    private lateinit var municipio: Municipio
    private var binding: ActivityLugaresBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLugaresBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        municipio = intent.getSerializableExtra("municipio") as Municipio
        usuario = intent.getSerializableExtra("usuario") as Usuario?
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initRV()
        getLugares()
    }

    private fun getLugares() {
        viewModel.getLugares(municipio.latitud, municipio.longitud).observe(this, Observer { it ->
            it?.let{
                adapter.setLugares(it)
            }
        })
    }

    private fun initRV() {
        adapter = AdapterLugar(this, R.layout.rowlugar)
        binding?.rvlugares?.adapter = adapter
        binding?.rvlugares?.layoutManager = LinearLayoutManager(this)
    }

    fun onClickLugar(v: View) {
        val lugar = v.tag as Lugar
        if (usuario==null) {
            Toast.makeText(this, "Lo siento zoquete, no login.....", Toast.LENGTH_LONG).show()
        }
        else {
            val intent = Intent(this, DetailLugarActivity::class.java)
            intent.putExtra("lugar", lugar)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }
    }
}