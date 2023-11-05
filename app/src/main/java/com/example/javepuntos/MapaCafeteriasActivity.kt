package com.example.javepuntos

import android.content.pm.ActivityInfo
import android.graphics.Matrix
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.javepuntos.databinding.ActivityMapaCafeteriasBinding

class MapaCafeteriasActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 0.5f

    private lateinit var binding: ActivityMapaCafeteriasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapaCafeteriasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Forzar la orientación horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Configurar Toolbar
//      setSupportActionBar(binding.toolbar)

        // Mostrar el botón de retroceso en el Toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Cargar imagen en ImageView
        binding.mapaImageView.setImageResource(R.drawable.mapacafeterias)

        imageView = binding.mapaImageView

        // Inicializar ScaleGestureDetector
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        // Configurar la imagen inicial
        configurarImagenInicial()
    }

    // Manejar el evento de hacer clic en el botón de retroceso en el Toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun configurarImagenInicial() {
        // Calcular el desplazamiento para centrar la imagen inicialmente
        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()
        val imageWidth = imageView.drawable.intrinsicWidth.toFloat()
        val imageHeight = imageView.drawable.intrinsicHeight.toFloat()

        translateX = (screenWidth - scaleFactor * imageWidth) / 2
        translateY = (screenHeight - scaleFactor * imageHeight) / 2

        // Aplicar la escala y desplazamiento a la matriz de la imagen
        val matrix = Matrix()
        matrix.setScale(scaleFactor, scaleFactor)
        matrix.postTranslate(translateX, translateY)  // Aplicar desplazamiento
        imageView.imageMatrix = matrix
    }

    private var lastTouchX: Float = 0f
    private var lastTouchY: Float = 0f
    private var translateX: Float = 0f
    private var translateY: Float = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            scaleGestureDetector?.onTouchEvent(event)

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastTouchX = event.x
                    lastTouchY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX: Float = event.x - lastTouchX
                    val deltaY: Float = event.y - lastTouchY

                    // Aplicar el desplazamiento a la matriz de la imagen
                    translateX += deltaX
                    translateY += deltaY

                    // Actualizar las últimas posiciones táctiles
                    lastTouchX = event.x
                    lastTouchY = event.y

                    // Aplicar la escala y desplazamiento a la matriz de la imagen
                    val matrix = Matrix()
                    matrix.setScale(scaleFactor, scaleFactor)
                    matrix.postTranslate(translateX, translateY)  // Aplicar desplazamiento
                    imageView.imageMatrix = matrix
                }
            }
        }
        return true
    }

    // Clase interna para gestionar el gesto de escala
    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            // Obtiene el factor de escala del gesto
            scaleFactor *= detector.scaleFactor

            // Limita el factor de escala para evitar que la imagen sea demasiado pequeña o grande
            scaleFactor = Math.max(0.43f, Math.min(scaleFactor, 10.0f))

            // Aplica la escala a la matriz de la imagen
            val matrix = Matrix()
            matrix.setScale(scaleFactor, scaleFactor)
            imageView.imageMatrix = matrix

            return true
        }
    }
}