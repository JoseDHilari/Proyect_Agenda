package hilari.abarca.my_first_apk.Adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Helpers.DatabaseHelper
import hilari.abarca.my_first_apk.Models.HoraModel
import hilari.abarca.my_first_apk.R

class ListaHorasAdapter: RecyclerView.Adapter<ListaHorasAdapter.ViewHolder>() {

    var lstHoras: List<HoraModel> = emptyList()
    var context: Context? = null

    fun actualizarLista(lst: List<HoraModel>) {
        lstHoras = lst
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tv_Dia = view.findViewById<TextView>(R.id.tv_Dia)
        val tv_Hora = view.findViewById<TextView>(R.id.tv_Hora)
        val ll_elemento = view.findViewById<LinearLayout>(R.id.ll_elemento)

        fun setValues(model: HoraModel) {
            tv_Dia.text = model.Dia.toString()
            tv_Hora.text = "${model.HoraInicio} - ${model.HoraFinal}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horas, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setValues(lstHoras[position])

        holder.ll_elemento.setOnClickListener {
            val horas = lstHoras[position]
            // Llama a la función para mostrar la alerta de confirmación
            showDeleteConfirmationDialog(horas, position)
        }
    }

    override fun getItemCount(): Int {
        return lstHoras.size
    }

    private fun showDeleteConfirmationDialog(hora: HoraModel, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmar Borrado")
        builder.setMessage("¿Estás seguro de que deseas borrar este dato?")

        // Botón de confirmación
        builder.setPositiveButton("Sí") { dialog, _ ->
            // Lógica para eliminar el dato de la base de datos
            val dbHelper = DatabaseHelper(context!!)
            val result = dbHelper.EliminarHora(hora.idHora)
            if (result > 0) {
                // Eliminar el dato de la lista y notificar el cambio
                lstHoras = lstHoras.toMutableList().apply { removeAt(position) }
                notifyItemRemoved(position)
                Toast.makeText(context, "Dato eliminado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        // Botón de cancelación
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss() // Cierra el diálogo sin hacer nada
        }

        // Muestra el cuadro de diálogo
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

}
