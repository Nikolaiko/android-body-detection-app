package com.nikolai.androidbodydetectionapp.cameraScreen

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Point
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Size
import android.view.View
import com.google.mlkit.vision.common.PointF3D
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark

class SkeletView(
    context: Context,
    attributes: AttributeSet
): View(context, attributes) {
    private val mainPaint = Paint(ANTI_ALIAS_FLAG)
    private var imageSize: Size = Size(0,0)
    private var viewSize: Size = Size(0,0)
    private var drawingPose: Pose? = null

    init {
        mainPaint.color = Color.RED
        mainPaint.strokeWidth = 4.0F
        mainPaint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewSize = Size(w,h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLandmarks(canvas)
        drawLines(canvas)
    }

    fun setParametres(pose: Pose, size: Size) {
        imageSize = size
        drawingPose = pose
        invalidate()
    }

    private  fun drawLandmarks(canvas: Canvas) {
       drawLandmark(canvas, drawingPose?.getPoseLandmark(PoseLandmark.NOSE))
       drawLandmark(canvas, drawingPose?.getPoseLandmark(PoseLandmark.RIGHT_EYE))
    }


    private fun drawLandmark(canvas: Canvas, poseLandmark: PoseLandmark?) {
        if (poseLandmark != null) {
            val newPoint = convertPosition(poseLandmark.position3D)
            canvas.drawCircle(newPoint.x, newPoint.y, 15F, mainPaint)
        }
    }


    private fun drawLines (canvas: Canvas) {
        val firstLandmark = drawingPose?.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        val secondLandmark = drawingPose?.getPoseLandmark(PoseLandmark.LEFT_WRIST)
        drawLine(canvas, firstLandmark, secondLandmark)
    }


    private fun drawLine(canvas: Canvas, firstLandmark: PoseLandmark?, secondLandmark: PoseLandmark?) {
        if (firstLandmark != null && secondLandmark != null) {
            val startingPoint: PointF = convertPosition(firstLandmark.position3D)
            val finishingPoint: PointF = convertPosition(secondLandmark.position3D)
            canvas.drawLine(startingPoint.x, startingPoint.y, finishingPoint.x, finishingPoint.y, mainPaint)
        }

    }


    private fun convertPosition(startingPoint: PointF3D): PointF {
        val x1 = startingPoint.x
        val y1 = startingPoint.y
        val w1 = imageSize.width
        val h1 = imageSize.height
        val w2 = viewSize.width
        val h2 = viewSize.height

        val x2 = x1*w2/w1
        val y2 = y1*h2/h1
        return PointF(x2, y2)
    }

}




/*


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewSize = Size(w, h)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawPoints(canvas)
        drawLines(canvas)

    }

    fun setParameters(pose: Pose, sourceSize: Size){
        detectorPose = pose
        sizeSource = sourceSize
        println("W: ${sizeSource.width}, H: ${sizeSource.height}")
        invalidate()
    }

    private fun drawLandmark(landMark: PoseLandmark, drawCanvas: Canvas?){
       val position = convertPoint(landMark.position3D)
        drawCanvas?.drawCircle(position.x,position.y, 15F, mainPaint)
    }

    private fun convertPoint (target: PointF3D): PointF {
        val x1 = target.x
        val y1 = target.y
        val w1 = sizeSource.width
        val h1 = sizeSource.height
        val w2 = viewSize.width
        val h2 = viewSize.height

        val x2 = x1*w2/w1
        val y2 = y1*h2/h1
        return PointF(x2, y2)
    }

    private fun drawPoints(canvas: Canvas?){
        var landMark = detectorPose?.getPoseLandmark(PoseLandmark.NOSE)
        if (landMark != null){
            drawLandmark(landMark, canvas)
        }
        landMark = detectorPose?.getPoseLandmark(PoseLandmark.LEFT_EYE_INNER)
        if (landMark != null) {
            drawLandmark(landMark, canvas)
        }
        landMark = detectorPose?.getPoseLandmark(PoseLandmark.LEFT_EYE_OUTER)
        landMark?.let{
            drawLandmark(it, canvas)
        }
        landMark = detectorPose?.getPoseLandmark(PoseLandmark.LEFT_EYE)
        landMark?.let {
            drawLandmark(it, canvas)
        }
        landMark = detectorPose?.getPoseLandmark(PoseLandmark.RIGHT_EYE_INNER)
        if (landMark != null) {
            drawLandmark(landMark, canvas)
        }
        landMark = detectorPose?.getPoseLandmark(PoseLandmark.RIGHT_EYE_OUTER)
        landMark?.let{
            drawLandmark(it, canvas)
        }
        landMark = detectorPose?.getPoseLandmark(PoseLandmark.RIGHT_EYE)
        landMark?.let {
            drawLandmark(it, canvas)
        }

        landMark = detectorPose?.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        landMark?.let {
            drawLandmark(it, canvas)
        }
        landMark = detectorPose?.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
        landMark?.let {
            drawLandmark(it, canvas)
        }

        landMark = detectorPose?.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        landMark?.let {
            drawLandmark(it, canvas)
        }
        landMark = detectorPose?.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
        landMark?.let {
            drawLandmark(it, canvas)

        }
        landMark = detectorPose?.getPoseLandmark(PoseLandmark.LEFT_MOUTH)
        landMark?.let {
            drawLandmark(it, canvas)
        }
        landMark = detectorPose?.getPoseLandmark(PoseLandmark.RIGHT_MOUTH)
        landMark?.let {
            drawLandmark(it, canvas)
        }

    }

    private fun drawLines(canvas: Canvas?){
        var firstLandMark = detectorPose?.getPoseLandmark(PoseLandmark.LEFT_MOUTH)
        var secondLandMark = detectorPose?.getPoseLandmark(PoseLandmark.RIGHT_MOUTH)
        if (firstLandMark!= null && secondLandMark!= null){
            drawLine(firstLandMark, secondLandMark, canvas)
        }
        firstLandMark = detectorPose?.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        secondLandMark = detectorPose?.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        if (firstLandMark!= null && secondLandMark!= null){
            drawLine(firstLandMark, secondLandMark, canvas)
        }
        firstLandMark = detectorPose?.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
        secondLandMark = detectorPose?.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
        if (firstLandMark!= null && secondLandMark!= null){
            drawLine(firstLandMark, secondLandMark, canvas)
        }

    }
    private fun drawLine(start:PoseLandmark, end:PoseLandmark, canvas: Canvas?){
        val startPoint = convertPoint(start.position3D)
        val endPoint = convertPoint(end.position3D)
        canvas?.drawLine(
            startPoint.x,
            startPoint.y,
            endPoint.x,
            endPoint.y,
            mainPaint
        )
    }
}


 */