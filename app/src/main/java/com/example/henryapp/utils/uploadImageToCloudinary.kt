package com.example.henryapp.utils

import android.content.Context
import android.net.Uri
import com.example.henryapp.BuildConfig
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import java.io.File
import java.io.IOException

fun uploadImageToCloudinary(context: Context, imageUri: Uri, onSuccess: (String) -> Unit, onError: (String) -> Unit) {

    val cloudName = BuildConfig.CLOUDINARY_CLOUD_NAME
    println("Cloudinary Cloud Name: $cloudName")
    val uploadPreset = BuildConfig.CLOUDINARY_UPLOAD_PRESET

    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(imageUri)

    if (inputStream == null) {
        onError("No se pudo abrir la imagen")
        return
    }

    val tempFile = File.createTempFile("upload", "jpg", context.cacheDir)
    tempFile.outputStream().use { outputStream ->
        inputStream.copyTo(outputStream)
    }

    val client = OkHttpClient()

    val requestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("file", tempFile.name, tempFile.asRequestBody("image/*".toMediaTypeOrNull()))
        .addFormDataPart("upload_preset", uploadPreset)
        .build()

    val request = Request.Builder()
        .url("https://api.cloudinary.com/v1_1/$cloudName/image/upload")
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onError(e.message ?: "Error en la subida")
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    onError("Error en la respuesta: ${response.body?.string() ?: "Sin mensaje"}")
                } else {
                    val responseBody = response.body?.string()
                    // parseo del JSON para obtener la URL
                    val json = org.json.JSONObject(responseBody ?: "{}")
                    val imageUrl = json.getString("secure_url")
                    onSuccess(imageUrl)
                }
            }
        }
    })
}
