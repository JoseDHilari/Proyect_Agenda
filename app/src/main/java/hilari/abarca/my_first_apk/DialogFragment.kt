import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import hilari.abarca.my_first_apk.R

class ImageZoomDialogFragment : DialogFragment() {

    companion object {
        private const val IMAGE_PATH = "image_path"

        fun newInstance(imagePath: String): ImageZoomDialogFragment {
            val args = Bundle()
            args.putString(IMAGE_PATH, imagePath)
            val fragment = ImageZoomDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_image_zoom, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagePath = arguments?.getString(IMAGE_PATH)
        val imageView: ImageView = view.findViewById(R.id.zoomableImageView)

        // Cargar la imagen usando Glide
        imagePath?.let {
            // Prueba con una imagen local primero para descartar problemas con la ruta
            Glide.with(this)
                .load(it) // Asegúrate de que esta ruta es válida
                .into(imageView)
        }

        // Inicializar el detector de gestos de escala
        scaleGestureDetector = ScaleGestureDetector(context!!, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor *= detector.scaleFactor
                scaleFactor = scaleFactor.coerceIn(0.5f, 3.0f) // Limita el zoom entre 0.5x y 3x

                imageView.scaleX = scaleFactor
                imageView.scaleY = scaleFactor
                return true
            }
        })

        // Aplicar el listener de touch para detectar el gesto de zoom
        imageView.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }
}
