package com.nikolai.androidbodydetectionapp.cameraScreen

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.nikolai.androidbodydetectionapp.R

class CameraActivity: AppCompatActivity() {
    private lateinit var cameraFuture: ListenableFuture<ProcessCameraProvider>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
println("ghjf")
        cameraFuture = ProcessCameraProvider.getInstance(this)
        cameraFuture.addListener({
            println("yui")
            val cameraProvider = cameraFuture.get()
            setCamera(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    fun setCamera(parametr: ProcessCameraProvider) {
        println("settingCamera")
        val preview = Preview.Builder().build()
        val frameView = findViewById<PreviewView>(R.id.camera_preview)
        preview.setSurfaceProvider(frameView.surfaceProvider)

        var cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        val ghbdtn = FrameAnalyzer()
        val collector = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        collector.setAnalyzer(mainExecutor, ghbdtn)
        parametr.bindToLifecycle(this, cameraSelector, preview, collector)
    }
}

/*
/*
class CameraScanActivity: AppCompatActivity() {
    private lateinit var cameraFuture: ListenableFuture<ProcessCameraProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_camera)

        cameraFuture = ProcessCameraProvider.getInstance(this)
        cameraFuture.addListener({
            val provider = cameraFuture.get()
            settingCamera(provider)

        }, ContextCompat.getMainExecutor(this))
    }

    private fun settingCamera(cameraProvider: ProcessCameraProvider){
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        val preview = Preview.Builder()
            .build()
        val cameraPreview = findViewById<PreviewView>(R.id.camera_preview)
        preview.setSurfaceProvider(cameraPreview.surfaceProvider)

        val imageAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        val viewPoint = findViewById<LandMarkView>(R.id.landMarkView)
        imageAnalyzer.setAnalyzer(mainExecutor, FrameAnalyzer(viewPoint))

        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)
   }
}
 */

 */