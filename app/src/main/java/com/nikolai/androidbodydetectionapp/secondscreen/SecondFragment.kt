package com.nikolai.androidbodydetectionapp.secondscreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.nikolai.androidbodydetectionapp.R
import com.nikolai.androidbodydetectionapp.cameraScreen.CameraActivity

class SecondFragment:Fragment() {

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        { isGranted ->
          if (isGranted == true) {
            startCamera()
          } else {

          }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn = view.findViewById<Button>(R.id.btn2)
        btn.setOnClickListener {
            val permission = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            )
            if (permission == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                launcher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    fun startCamera(){
        println("tyu")
        val intent = Intent(requireContext(), CameraActivity::class.java)
        startActivity(intent)
    }
}

