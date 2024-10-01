package hilari.abarca.my_first_apk.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hilari.abarca.my_first_apk.Models.MultimediaModel
import hilari.abarca.my_first_apk.R

class ListaMultimediaAdapter(
    private val context: Context,
    private var lstMultimedia: List<MultimediaModel>
) : RecyclerView.Adapter<ListaMultimediaAdapter.ViewHolder>() {

    // Actualizar la lista de multimedia
    fun actualizarLista(lst: List<MultimediaModel>) {
        lstMultimedia = lst
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPreview: ImageView = view.findViewById(R.id.ivPreview)
        val tvFechaCreacion: TextView = view.findViewById(R.id.tvFechaCreacion)

        fun setValues(multimedia: MultimediaModel, context: Context) {
            tvFechaCreacion.text = multimedia.fechaCreacion

            if (multimedia.esImagen()) {
                Glide.with(context)
                    .load(multimedia.archivoMultimedia)
                    .into(ivPreview)
            } else if (multimedia.esVideo()) {
                Glide.with(context)
                    .load(multimedia.archivoMultimedia)
                    .into(ivPreview)
            } else {
                ivPreview.setImageResource(R.drawable.ic_file)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_multimedia, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lstMultimedia.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val multimedia = lstMultimedia[position]
        holder.setValues(multimedia, context)

        holder.itemView.setOnClickListener {
            // Mostrar el diálogo de zoom de imagen
            val fragment = ImageZoomDialogFragment.newInstance(multimedia.archivoMultimedia)
            (context as AppCompatActivity).supportFragmentManager.let {
                fragment.show(it, "ImageZoomDialog")
            }
        }
    }
}
