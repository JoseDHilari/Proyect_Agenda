package hilari.abarca.my_first_apk.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Models.RecordatorioModel
import hilari.abarca.my_first_apk.R
import hilari.abarca.my_first_apk.VerNotaActivity

class ListaRecordatoriosAdapter : RecyclerView.Adapter<ListaRecordatoriosAdapter.ViewHolder>() {

    var lstRecordatorios: List<RecordatorioModel> = emptyList()
    var context: Context? = null

    fun actualizarLista(lst: List<RecordatorioModel>) {
        lstRecordatorios = lst
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_Fecha = view.findViewById<TextView>(R.id.tv_Fecha)
        val tv_Hora = view.findViewById<TextView>(R.id.tv_Hora)
        val tv_NombreAlarma = view.findViewById<TextView>(R.id.tv_NombreAlarma)
        val ll_elemento = view.findViewById<LinearLayout>(R.id.ll_elemento)

        fun setValues(model: RecordatorioModel) {
            tv_Fecha.text = model.Fecha
            tv_Hora.text = model.Hora
            tv_NombreAlarma.text = model.Recordatorio
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recordatorio, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setValues(lstRecordatorios[position])
        holder.ll_elemento.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return lstRecordatorios.size
    }
}
