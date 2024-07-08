package hilari.abarca.my_first_apk.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Models.CursosModel
import hilari.abarca.my_first_apk.R

class ListaCursosAdapter:RecyclerView.Adapter<ListaCursosAdapter.ViewHolder>() {

    var lstCursos:List<CursosModel> = emptyList()

    fun actualizarLista(lst:List<CursosModel>){
        lstCursos = lst
        notifyDataSetChanged()
    }
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val tv_Hora = view.findViewById<TextView>(R.id.tv_Hora)
        val tv_NombreCurso = view.findViewById<TextView>(R.id.tv_NombreCurso)
        val iv_Alarma = view.findViewById<ImageView>(R.id.iv_Alarma)

        fun setValues(model: CursosModel){
            tv_Hora.setText(model.HoraInicio.toString() + " - " + model.HoraFinal.toString())
            tv_NombreCurso.setText(model.NombreCurso.toString())

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cursos,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lstCursos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setValues(lstCursos[position])
    }
}