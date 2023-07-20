package net.azarquiel.caravanapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import net.azarquiel.caravanapp.R
import net.azarquiel.caravanapp.adapters.AdapterCCAA
import net.azarquiel.caravanapp.databinding.ActivityMainBinding
import net.azarquiel.caravanapp.model.Comunidad
import net.azarquiel.caravanapp.model.Usuario
import net.azarquiel.caravanapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private var titulo = String()
    private var usuario: Usuario?=null
    private lateinit var shareP: SharedPreferences
    private lateinit var adapter: AdapterCCAA
    private lateinit var comunidades: List<Comunidad>
    private lateinit var viewModel: MainViewModel
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)
        titulo = title.toString()
        shareP = getSharedPreferences("datos", Context.MODE_PRIVATE)
        getUsuarioSH()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initRV()
        getComunidades()
    }

    private fun saveUsuarioSH(usuario: Usuario) {
        val editor = shareP.edit()
        editor.putString("usuario", Gson().toJson(usuario))
        editor.commit()
        title = "$titulo - ${usuario.nick}"
    }

    private fun getUsuarioSH() {
        val usuarioTXT = shareP.getString("usuario","nosta")
        if (!usuarioTXT.equals("nosta")){
            usuario = Gson().fromJson(usuarioTXT, Usuario::class.java)
            title = "$titulo - ${usuario!!.nick}"
        }
    }
    private fun removeUsuarioSH() {
        val editor = shareP.edit()
        editor.remove("usuario")
        editor.commit()
        usuario = null
        title = titulo
    }

    private fun initRV() {
        adapter = AdapterCCAA(this,R.layout.rowccaa)
        binding?.contentMain?.rvccaa?.adapter = adapter
        binding?.contentMain?.rvccaa?.layoutManager = LinearLayoutManager(this)
    }

    private fun getComunidades() {
        viewModel.getComunidades().observe(this, Observer { it ->
            it?.let{
                comunidades = it
                showComunidades()
            }
        })
    }

    private fun showComunidades() {
        adapter.setComunidades(comunidades)
    }

    fun onClickCCAA(v: View) {
        val comunidad = v.tag as Comunidad
        val intent = Intent(this, ProvinciasActivity::class.java)
        intent.putExtra("comunidad", comunidad)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_login -> {
                dialogLoginRegister()
                true
            }
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun msg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun dialogLoginRegister() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Login/Register")
        val ll = LinearLayout(this)
        ll.setPadding(30,30,30,30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        lp.setMargins(0,50,0,50)

        val textInputLayoutNick = TextInputLayout(this)
        textInputLayoutNick.layoutParams = lp
        val etnick = EditText(this)
        etnick.setPadding(0, 80, 0, 80)
        etnick.textSize = 20.0F
        etnick.hint = "Nick"
        textInputLayoutNick.addView(etnick)

        val textInputLayoutPass = TextInputLayout(this)
        textInputLayoutPass.layoutParams = lp
        val etpass = EditText(this)
        etpass.setPadding(0, 80, 0, 80)
        etpass.textSize = 20.0F
        etpass.hint = "Pass"
        textInputLayoutPass.addView(etpass)

        ll.addView(textInputLayoutNick)
        ll.addView(textInputLayoutPass)

        builder.setView(ll)

        builder.setPositiveButton("Aceptar") { dialog, which ->
            val usuario = Usuario("",etnick.text.toString(), etpass.text.toString())
            loginregister(usuario)
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
        }

        builder.show()
    }

    private fun loginregister(usuario: Usuario) {
        viewModel.getUserByNickAndPass(usuario).observe(this, Observer { it ->
            if (it==null) {
                viewModel.saveUsuario(usuario).observe(this, Observer { it ->
                    it?.let {
                        this.usuario = it
                        saveUsuarioSH(it)
                        msg("User Created....")
                    }
                })
            }
            else {
                this.usuario = it
                saveUsuarioSH(it)
                msg("Login OK....")
            }
        })
    }

    private fun logout() {
        removeUsuarioSH()
        msg("Logout ok....")
    }

}