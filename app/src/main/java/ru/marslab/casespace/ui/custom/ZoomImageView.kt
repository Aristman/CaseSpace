package ru.marslab.casespace.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.abs


class ZoomImageView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatImageView(context, attrs, defStyleAttr) {
    // Отслеживание касаний пальцев
    private var scaleDetector: ScaleGestureDetector? = null

    // текущий коэффициент масштабирования изображения
    private var scaleFactor = 1f

    // конструкторы
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, defStyleAttr = 0)
    constructor(context: Context) : this(context, attrs = null, defStyleAttr = 0)

    // начальный коэффициент масштабирования
    private val normalScaleFactor = 1f

    // число на которое будет увеличиваться коэффициент масштабирования при анимации
    private val increasingValue = .02f

    // специальное число, которое уменьшает скорость масштабирования при уменьшении изображения
    private val decreasingDivisor = 1.032

    // минимальный коэффициент масштабирования
    private val minScaleFactor = 0.05f

    // максимальный коэффициент масштабирования
    private val maxScaleFactor = 5.0f

    // задержка для анимации
    private val delay = 4L

    init {
        // инициализация нашего ScaleGestureDetector'а
        scaleDetector = ScaleGestureDetector(context, ScaleListener())
    }

    // анимация возвращения к нормальному коэффициенту
    // срабатывает, когда мы уменьшаем изображение и отпускаем его
    val animationRunnable: Runnable = object : Runnable {
        override fun run() {
            // пока текущий коэффициент масштабирования меньше нормального
            // увеличиваем его до нормального
            if (scaleFactor <= normalScaleFactor) {
                scaleFactor += increasingValue
                // постоянно перерисовываем наше View
                invalidate()
                // снова выполняем анимацию, пока условие не станет ложным
                handler.postDelayed(this, delay)
            } else {
                // после завершения анимации, присваем текущему коэффициенту
                // начальное значение и перерисовываем View
                scaleFactor = normalScaleFactor
                invalidate()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        // отслеживаем касания пальцев
        scaleDetector!!.onTouchEvent(ev)
        // запускаем анимацию, если мы уменьшили изображение и отпустили его
        if (ev.action == MotionEvent.ACTION_UP && scaleFactor < normalScaleFactor) {
            // отменяем предыдущую анимацию
            handler.removeCallbacks(animationRunnable)
            // запускаем новую
            handler.postDelayed(animationRunnable, 4L)
        }
        return true
    }


    override fun onDraw(canvas: Canvas) {
        canvas.save()

        // масштабируем canvas вместе с нашим изображением
        // Обратите внимание, что мы передаем getWidth() / 2 и getHeight() / 2
        // для того, чтобы изображение масштабировалось от центра
        canvas.scale(scaleFactor, scaleFactor, (width / 2).toFloat(), (height / 2).toFloat())
        super.onDraw(canvas)
        canvas.restore()
    }

    // чувствительность срабатывания, которую я подобрал экспериментально
    private val sensitivity = 10

    private inner class ScaleListener : SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            // если значения перемещения наших пальцев больше sensitivity то мы считаем,
            // что изображение масштабируется, в противном случае - ложное срабатывание
            if (abs(detector.currentSpan - detector.previousSpan) > sensitivity) {
                val scale = detector.scaleFactor

                // при уменьшении я заметил что скорость масштабирования была очень быстрая,
                // поэтому я решил поделить scale на специальный коэффициент decreasingDivisor, который я тоже подобрал
                // экспериментально
                scaleFactor *= if (scale < normalScaleFactor) scale / decreasingDivisor.toFloat() else scale

                // ограничиваем максимальное масштабирование в 5.0 и минимальное в 0.05
                scaleFactor = minScaleFactor.coerceAtLeast(scaleFactor.coerceAtMost(maxScaleFactor))
                invalidate()
            }
            return true
        }
    }
}