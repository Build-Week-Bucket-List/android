package com.rybarstudios.bucketlist.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.EditText

class JournalDetailCustomView(context: Context, attrs: AttributeSet?): EditText(context, attrs) {

    private val rect = Rect()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val offset = 10


    override fun onDraw(canvas: Canvas?) {
        var count: Int = lineCount

        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE

        for(i in 0 until count) {
            val baseLine = getLineBounds(i, rect)
            canvas?.drawLine(rect.left.toFloat(), (baseLine + offset).toFloat(), rect.right.toFloat(), (baseLine + offset).toFloat(), paint)
        }

        super.onDraw(canvas)
    }
}