package net.azarquiel.caravanapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.caravanapp.R
import net.azarquiel.caravanapp.model.Comunidad

/**
 * Created by pacopulido on 9/10/18.
 */
class AdapterCCAA(val context: Context,
                  val layout: Int
                    ) : RecyclerView.Adapter<AdapterCCAA.ViewHolder>() {

    private var dataList: List<Comunidad> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setComunidades(comunidades: List<Comunidad>) {
        this.dataList = comunidades
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Comunidad){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val ivrowccaa = itemView.findViewById(R.id.ivrowccaa) as ImageView
            val tvrowccaa = itemView.findViewById(R.id.tvrowccaa) as TextView


            tvrowccaa.text = dataItem.nombre

            val id = context.resources.getIdentifier("ccaa${dataItem.id}","drawable",context.packageName)
            ivrowccaa.setImageResource(id)

            itemView.tag = dataItem

        }

    }
}