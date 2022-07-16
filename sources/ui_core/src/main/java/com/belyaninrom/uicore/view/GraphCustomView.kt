package com.belyaninrom.uicore.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.belyaninrom.uicore.R
import com.belyaninrom.uicore.model.CurrencyView
import java.text.SimpleDateFormat
import java.util.*

class GraphCustomView(context: Context, attrs: AttributeSet?): View(context, attrs) {

    val density = context.resources.displayMetrics.density
    val scaledDensity = context.resources.displayMetrics.scaledDensity

    val widthMax = (200 * density).toInt()
    val heightMax = (225 * density).toInt()
    val dotsColor: Int
    val gradientColor: Int
    val lineColor: Int
    val backgroundColor: Int
    val textColor: Int
    val selectedDotsColor: Int
    val sizeDateText = 12 * scaledDensity


    val items = ArrayList<CurrencyView>()
    var dateFormat = SimpleDateFormat("yyyy-MM-dd")
    var startDate = 0L
    var endDate = 1L
    var dateRange = 1L
    var minPrice = 0L
    var maxPrice = 1L
    var priceRange = 1L

    var selectedX: Float? = null
    var selectedY: Float? = null

    var paddingBottomGraph = 55 * density
    var paddingTopGraph = 55 * density
    val paddingStartGraph = (3 * density) +  (16 * scaledDensity)
    val paddingEndGraph = (3 * density) +  (16 * scaledDensity)
    lateinit var gradient: LinearGradient

    var path = Path()
    var erasePath = Path()

    var heightGraph: Float = 1F
    var widthGraph: Float = 1F

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.GraphCustomView)
        try {
            dotsColor = typedArray.getColor(R.styleable.GraphCustomView_dotsColor, Color.BLACK)
            gradientColor = typedArray.getColor(R.styleable.GraphCustomView_gradientColor, Color.GREEN)
            lineColor = typedArray.getColor(R.styleable.GraphCustomView_lineColor, Color.GREEN)
            backgroundColor = typedArray.getColor(R.styleable.GraphCustomView_backgroundColor, Color.GRAY)
            textColor = typedArray.getColor(R.styleable.GraphCustomView_textColor, Color.BLACK)
            selectedDotsColor = typedArray.getColor(R.styleable.GraphCustomView_selectedDotsColor, Color.CYAN)
        } finally {
            typedArray.recycle()
        }
    }

    val paint = Paint().apply {
        color = lineColor
        flags = Paint.ANTI_ALIAS_FLAG
        strokeWidth = 20f * density
        textSize = sizeDateText
    }

    val paintClear = Paint().apply {
        color = backgroundColor
        strokeWidth = widthMax.toFloat()
    }


    fun setItems(tradeApplications: List<CurrencyView>) {
        items.clear()
        items.addAll(
            tradeApplications.map {
                it.tradeDataDate = dateFormat.parse(it.tradeDate).time
                it
            }.toList()
        )
        val price = tradeApplications.map { it.rate.toDouble() }
        minPrice = price.minOf { it }.toLong()
        maxPrice = price.maxOf { it }.toLong()
        priceRange = maxPrice - minPrice
        if (priceRange == 0L)
            priceRange = 1L

        if (tradeApplications.isNotEmpty()) {
            startDate = dateFormat.parse(items.first().tradeDate).time
            endDate = dateFormat.parse(items.last().tradeDate).time
            dateRange = endDate - startDate
            if (dateRange == 0L)
                dateRange = 1L
        }

        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val modeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val modeHeight = MeasureSpec.getMode(heightMeasureSpec)
        val widthMeasure = MeasureSpec.getSize(widthMeasureSpec)
        val heightMeasure = MeasureSpec.getSize(heightMeasureSpec)
        val width = measureSize(modeWidth, widthMeasure, widthMax)
        val height = measureSize(modeHeight, heightMeasure, heightMax)

        heightGraph = (height - paddingBottomGraph - paddingTopGraph)
        widthGraph = (width - paddingEndGraph - paddingStartGraph)

        gradient = LinearGradient(
            0F, height.toFloat() - sizeDateText - (4 * density), 0F,
            0F, Color.TRANSPARENT, gradientColor, Shader.TileMode.CLAMP
        )
        setMeasuredDimension(width, height)
    }

    private fun measureSize(mode: Int, size: Int, sizeMax: Int) = when (mode) {
        MeasureSpec.EXACTLY -> {
            size
        }
        MeasureSpec.AT_MOST -> {
            if (sizeMax <= size) {
                sizeMax
            } else {
                size
            }
        }
        MeasureSpec.UNSPECIFIED -> {
            sizeMax
        }
        else -> {
            size
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            selectedX = event.x
            selectedY = event.y
            invalidate()
            return true
        }
        if (event?.action == MotionEvent.ACTION_MOVE) {
            selectedX = event.x
            selectedY = event.y
            invalidate()
            return true
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        paint.color = backgroundColor
        paint.style = Paint.Style.FILL
        canvas?.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paint)

        paint.shader = gradient
        canvas?.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paint)
        paint.shader = null
        if (items.size == 0)
            return

        drawGraphLine(canvas)
        drawTouchTextDots(canvas)
    }

    val textDateFormat = SimpleDateFormat("dd.MM.yy")

    private fun drawTouchTextDots(canvas: Canvas?) {

        if (selectedX == null)
            return

        var firstDistance = width.toDouble()
        var nearDots: CurrencyView? = null
        drawDots(){item, position, x, y ->
            val distance = Math.abs(x.toDouble() - (selectedX?.toDouble() ?: 0.0))
            if (firstDistance > distance) {
                firstDistance = distance
                nearDots = item
            }
        }

        paint.color = textColor
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 1f
        val text = "${textDateFormat.format(nearDots?.tradeDataDate)}  ${nearDots?.rate}"
        val widthText = paint.measureText(text)
        canvas?.drawText(text, ((width / 2) - (widthText / 2)).toFloat(), (height - (14 * scaledDensity)), paint)

        nearDots?.let {
            val xD = calculateCoordinateX(it)
            val yD = calculateCoordinateY(it)
            paint.color = selectedDotsColor
            paint.style = Paint.Style.FILL
            canvas?.drawCircle(xD, yD, (5 * density), paint)
        }
        Log.d("GraphCustomView", "tradeDate ${nearDots?.tradeDate}")
    }

    private fun drawTouchLine(canvas: Canvas?) {
        selectedX?.let {
            paint.color = Color.GRAY
            paint.strokeWidth = 1f * density
            canvas?.drawLine(it, 15f, it, height.toFloat() - (density * 33), paint)
        }
    }


    private fun drawGraphLine(canvas: Canvas?) {
        paint.strokeWidth = 1.5f * density
        paint.color = lineColor
        var lastYDots = 0F
        drawDots { item, position, x, y ->
            lastYDots = y
            if (position == 1) {
                path.moveTo(0F, y)
                path.lineTo(x, y)
                erasePath.moveTo(0F, 0F)
                erasePath.lineTo(0F, y)
                erasePath.lineTo(x, y)
            } else {
                path.lineTo(x, y)
                erasePath.lineTo(x, y)
            }
        }

        path.lineTo(width.toFloat(), lastYDots)
        erasePath.lineTo(width.toFloat(), lastYDots)
        erasePath.lineTo(width.toFloat(), 0F)
        erasePath.lineTo(height.toFloat(), 0F)
        erasePath.lineTo(0F, 0F)

        paint.color = lineColor
        paint.style = Paint.Style.STROKE
        canvas?.drawPath(erasePath, paintClear)
        canvas?.drawPath(path, paint)

        paint.strokeWidth = 4 * density
//        drawDots { item, position, x, y ->
//            paint.color = dotsColor
//            canvas?.drawCircle(x, y, 1 * density, paint)
//        }
    }


    fun drawDots(callBack: (item: CurrencyView, position: Int, x: Float, y: Float) -> Unit) {
        if (items.size == 1) {
            callBack(items[0], 1, width / 2F, height / 2F)
            return
        }

        for (i in 0 until items.size) {
            val item = items[i]
            val xD = calculateCoordinateX(item)
            val yD = calculateCoordinateY(item)

            Log.d (
                "HistoryTradeView",
                "x $xD tradeDataDate ${item.tradeDataDate} startDate $startDate dateRange $dateRange widthGraph $widthGraph"
            )

            callBack(item, i + 1, xD, yD)
        }
    }

    fun calculateCoordinateX(item: CurrencyView): Float {
        return (((item.tradeDataDate.toDouble() - startDate.toDouble()) / dateRange.toDouble()) * (widthGraph.toDouble())).toFloat() + paddingEndGraph
    }

    fun calculateCoordinateY(item: CurrencyView): Float {
        return heightGraph - ((((item.rate) - minPrice.toDouble()) / priceRange.toDouble()) * heightGraph).toFloat() + paddingTopGraph
    }
}