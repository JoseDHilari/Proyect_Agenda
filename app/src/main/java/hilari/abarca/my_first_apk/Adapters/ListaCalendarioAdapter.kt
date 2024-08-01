package hilari.abarca.my_first_apk.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Models.CalendarioModel
import hilari.abarca.my_first_apk.R

class ListaCalendarioAdapter : RecyclerView.Adapter<ListaCalendarioAdapter.ViewHolder>(){

    var lstCalendario : List<CalendarioModel> = emptyList()
    var context: Context? = null

    fun actualizarLista(lst: List<CalendarioModel>) {
        lstCalendario = lst
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_Dia = view.findViewById<TextView>(R.id.tv_Dia)
        val tv_Nombre = view.findViewById<TextView>(R.id.tv_Nombre)
        val ll_elemento = view.findViewById<LinearLayout>(R.id.ll_elemento)

        fun setValues(model: CalendarioModel) {
            tv_Dia.text = model.dia.toString()
            tv_Nombre.text = model.nombre
        }
    }
    override fun onCreateViewHolder(parent:ViewGroup,viewType:Int):ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendario, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setValues(lstCalendario[position])
        holder.ll_elemento.setOnClickListener{

        }
    }

    override fun getItemCount(): Int {
        return lstCalendario.size
    }

}