package com.example.gestok.network.service

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CloudinaryService {

    @Multipart
    @POST("image/upload")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("upload_preset") uploadPreset: String
    ): Call<CloudinaryUploadResponse>
}