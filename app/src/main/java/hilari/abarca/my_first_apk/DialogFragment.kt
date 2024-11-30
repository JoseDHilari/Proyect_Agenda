import android.annotation.SuppressLint
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Bundle
import android.view.*
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

    private lateinit var imageView: ImageView
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var gestureDetector: GestureDetector
    private var matrix = Matrix()
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
        imageView = view.findViewById(R.id.zoomableImageView)

        // Cargar la imagen usando Glide
        imagePath?.let {
            Glide.with(this)
                .load(it)
                .into(imageView)
        }

        // Inicializar Matrix
        imageView.imageMatrix = matrix
        imageView.scaleType = ImageView.ScaleType.MATRIX

        // Inicializar el detector de gestos para manejar el zoom
        scaleGestureDetector = ScaleGestureDetector(context!!, ScaleListener())

        // Inicializar el detector de gestos para manejar el desplazamiento
        gestureDetector = GestureDetector(context, GestureListener())

        // Aplicar el listener de touch para detectar gestos
        imageView.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            gestureDetector.onTouchEvent(event)
            true
        }
    }

    // Escuchar los eventos de zoom (pinch-to-zoom)
    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            // Actualizamos el factor de escala basándonos en el gesto de zoom
            scaleFactor *= detector.scaleFactor
            // Limitar el zoom entre 1.0x (tamaño original) y 5.0x (zoom máximo)
            scaleFactor = scaleFactor.coerceIn(1.0f, 5.0f)

            // Aplicamos el zoom solo si el factor está dentro del rango permitido
            matrix.postScale(
                detector.scaleFactor, detector.scaleFactor,
                detector.focusX, detector.focusY
            )

            // Ajustamos la posición de la imagen para evitar que quede fuera de la pantalla
            correctImagePosition()

            // Aplicar la transformación
            imageView.imageMatrix = matrix
            return true
        }
    }

    // Método para corregir la posición de la imagen y evitar que se salga de los límites
    private fun correctImagePosition() {
        val drawable = imageView.drawable ?: return
        val bounds = RectF(0f, 0f, drawable.intrinsicWidth.toFloat(), drawable.intrinsicHeight.toFloat())
        matrix.mapRect(bounds)

        val width = imageView.width.toFloat()
        val height = imageView.height.toFloat()

        var dx = 0f
        var dy = 0f

        // Ajustar horizontalmente
        if (bounds.width() < width) {
            dx = (width - bounds.width()) / 2 - bounds.left
        } else if (bounds.left > 0) {
            dx = -bounds.left
        } else if (bounds.right < width) {
            dx = width - bounds.right
        }

        // Ajustar verticalmente
        if (bounds.height() < height) {
            dy = (height - bounds.height()) / 2 - bounds.top
        } else if (bounds.top > 0) {
            dy = -bounds.top
        } else if (bounds.bottom < height) {
            dy = height - bounds.bottom
        }

        matrix.postTranslate(dx, dy)
    }


    // Escuchar los eventos de desplazamiento (scroll)
    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            matrix.postTranslate(-distanceX, -distanceY)
            imageView.imageMatrix = matrix
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            resetZoom()
            return true
        }
    }

    // Reiniciar el zoom al valor original
    private fun resetZoom() {
        matrix.reset()
        imageView.imageMatrix = matrix
        scaleFactor = 1.0f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }
}
