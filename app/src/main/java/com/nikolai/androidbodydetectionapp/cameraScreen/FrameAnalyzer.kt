package com.nikolai.androidbodydetectionapp.cameraScreen

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions

class FrameAnalyzer: ImageAnalysis.Analyzer {
    val options = PoseDetectorOptions.Builder()
        .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
        .build()
    val detector = PoseDetection.getClient(options)
    override fun analyze(image: ImageProxy) {
        println("работает?")
        image.close()
    }

}


/*
class FrameAnalyzer(
    private val viewPoint: LandMarkView
):ImageAnalysis.Analyzer {
    private val options = PoseDetectorOptions.Builder()
        .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
        .build()
    private val detector = PoseDetection.getClient(options)
    override fun analyze(image: ImageProxy) {
        val mediaImage = image.image
        if (mediaImage!=null) {
            val imageForDetector = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)
            detector.process(imageForDetector)
                .addOnSuccessListener { resultPose ->
                   val size = Size (
                       min(image.width, image.height),
                       max(image.width, image.height)
                   )
                    viewPoint.setParameters(resultPose, size)
                    image.close()
                }
                .addOnFailureListener{
                    println("распознать не удалось : $it")
                    image.close()
                }
        }

    }

}

 */
