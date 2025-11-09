package com.example.levelupgamer.ui.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.zxing.integration.android.IntentIntegrator

@Composable
fun ScannerScreen() {
    val context = LocalContext.current

    // launcher que recibe el resultado del escáner
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        val scanResult = IntentIntegrator.parseActivityResult(
            result.resultCode,
            result.data
        )
        if (scanResult == null || scanResult.contents == null) {
            Toast.makeText(context, "Escaneo cancelado", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Resultado: ${scanResult.contents}", Toast.LENGTH_LONG).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            // intentamos sacar la Activity real
            val activity = context.findActivity()
            if (activity == null) {
                Toast.makeText(context, "No se pudo abrir la cámara (no hay Activity)", Toast.LENGTH_LONG).show()
                return@Button
            }

            val integrator = IntentIntegrator(activity)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.setPrompt("Escanea un código QR")
            integrator.setCameraId(0)
            integrator.setBeepEnabled(true)
            integrator.setBarcodeImageEnabled(true)

            // lanzamos el intent del integrator usando el launcher de Compose
            launcher.launch(integrator.createScanIntent())
        }) {
            Text("Iniciar Escáner")
        }
    }
}

/**
 * Extensión para obtener la Activity desde un Context en Compose
 */
private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is android.content.ContextWrapper -> baseContext.findActivity()
    else -> null
}
