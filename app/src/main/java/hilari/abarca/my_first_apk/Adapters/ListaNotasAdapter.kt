package hilari.abarca.my_first_apk.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Models.NotaModel
import hilari.abarca.my_first_apk.R
import hilari.abarca.my_first_apk.VerNotaActivity

class ListaNotasAdapter:RecyclerView.Adapter<ListaNotasAdapter.ViewHolder>() {

    var lstNotas:List<NotaModel> = emptyList()
    var context:Context? = null

    fun actualizarLista(lst:List<NotaModel>){
        lstNotas = lst
        notifyDataSetChanged()
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tv_Nombre = view.findViewById<TextView>(R.id.NotaName)
        val tv_Descripcion = view.findViewById<TextView>(R.id.NotaDescripcion)
        val ll_elemento = view.findViewById<LinearLayout>(R.id.ll_elemento)

        fun setValues(model: NotaModel){
            tv_Nombre.setText(model.Nombre.toString())
            tv_Descripcion.setText(model.Descripcion.toString())
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notas,parent,false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setValues(lstNotas[position])
        holder.ll_elemento.setOnClickListener{

            val Nota = lstNotas[position]

            val intent = Intent(context,VerNotaActivity::class.java)
            intent.putExtra("idNota",Nota.idNota)
            intent.putExtra("idCurso",Nota.idCurso)
            context!!.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return lstNotas.size
    }

}