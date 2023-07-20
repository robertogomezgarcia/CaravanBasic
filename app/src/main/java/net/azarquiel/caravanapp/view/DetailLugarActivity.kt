package net.azarquiel.caravanapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.azarquiel.caravanapp.databinding.ActivityDetailLugarBinding
import net.azarquiel.caravanapp.model.Foto
import net.azarquiel.caravanapp.model.Lugar
import net.azarquiel.caravanapp.model.Punto
import net.azarquiel.caravanapp.model.Usuario
import net.azarquiel.caravanapp.viewmodel.MainViewModel

class DetailLugarActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var usuario: Usuario
    private lateinit var fotos: List<Foto>
    private lateinit var viewModel: MainViewModel
    private lateinit var lugar: Lugar
    private var binding: ActivityDetailLugarBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLugarBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        observer()
        lugar = intent.getSerializableExtra("lugar") as Lugar
        usuario = intent.getSerializableExtra("usuario") as Usuario
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        getDatos()

    }

    private fun observer() {
        var ivcarita: ImageView
        for (i in 0 until binding?.linearcaritas?.childCount!!) {
            ivcarita = binding?.linearcaritas!!.getChildAt(i) as ImageView
            ivcarita.setOnClickListener(this)
        }
    }

    private fun getDatos() {
        viewModel.getFotos(lugar.id).observe(this, Observer { it ->
            it?.let{
                fotos = it
                showDatos()
                getMediaPuntos()
            }
        })
    }

    private fun getMediaPuntos() {
        viewModel.getMediaPuntosByLugar(lugar.id).observe(this, Observer { it ->
            it?.let{
                Log.d("paco", it)
                showMedia(it)
            }
        })
    }

    private fun showDatos() {
        binding?.tvtitulodetalle?.text = lugar.titre
        binding?.tvdescripciondetalle?.text = lugar.description_es
        var iv:ImageView
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        lp.setMargins(0,0,30,0)
        for (foto in fotos) {
            iv = ImageView(this)
            iv.layoutParams = lp
            // foto de internet a traves de Picasso
            Picasso.get().load(foto.link_large).into(iv)
            binding?.lhdetalle?.addView(iv)
        }
    }
    private fun showMedia(avg:String){
        if (avg!="0") {
            val avgfloat:Float = avg.toFloat()
            val avgint = Math.round(avgfloat)
            val id = resources.getIdentifier("ic_$avgint", "drawable", packageName)
            binding?.ivcarita?.setImageResource(id)
        }
    }

    override fun onClick(v: View?) {
        val n = v!!.tag as String
        val punto = Punto("0", usuario.id, lugar.id, n)
        viewModel.savePuntos(lugar.id, punto).observe(this, Observer { it ->
            it?.let{
                Toast.makeText(this, "Anotados $n puntos al lugar ${lugar.titre}", Toast.LENGTH_LONG).show()
                getMediaPuntos()
            }
        })

    }
}