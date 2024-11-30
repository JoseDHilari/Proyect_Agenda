package hilari.abarca.my_first_apk.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hilari.abarca.my_first_apk.Models.ArchivosModel
import hilari.abarca.my_first_apk.R

class ListaArchivosAdapter(
    private var archivosList: List<ArchivosModel>,
    private val onFileClick: (ArchivosModel) -> Unit // Recibe un lambda para manejar el clic
) : RecyclerView.Adapter<ListaArchivosAdapter.ArchivosViewHolder>() {

    class ArchivosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreArchivo: TextView = itemView.findViewById(R.id.archivo_nombre)
        val fecha: TextView = itemView.findViewById(R.id.archivo_fecha)
        val iconoArchivo: ImageView = itemView.findViewById(R.id.archivo_icono)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchivosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_archivos, parent, false)
        return ArchivosViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArchivosViewHolder, position: Int) {
        val archivo = archivosList[position]
        holder.nombreArchivo.text = archivo.nombreArchivo
        holder.fecha.text = archivo.fecha

        // Asigna el icono basado en la extensión del archivo
        val extension = archivo.rutaArchivo.substringAfterLast(".")
        when (extension) {
            "pdf" -> holder.iconoArchivo.setImageResource(R.drawable.ic_pdf)
            "docx" -> holder.iconoArchivo.setImageResource(R.drawable.ic_doc)
            "pptx" -> holder.iconoArchivo.setImageResource(R.drawable.ic_ppt)
            else -> holder.iconoArchivo.setImageResource(R.drawable.ic_file)
        }

        // Manejar el click en el item para abrir el archivo
        holder.itemView.setOnClickListener {
            onFileClick(archivo) // Llama al lambda pasando el archivo
        }
    }

    override fun getItemCount(): Int = archivosList.size

    fun updateData(newArchivosList: List<ArchivosModel>) {
        archivosList = newArchivosList
        notifyDataSetChanged()
    }
}
