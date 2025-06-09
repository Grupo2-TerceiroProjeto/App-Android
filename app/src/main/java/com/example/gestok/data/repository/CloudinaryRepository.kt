package com.example.gestok.data.repository

import com.example.gestok.domain.model.product.CloudinaryUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CloudinaryRepository {

    @Multipart
    @POST("image/upload")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("upload_preset") uploadPreset: RequestBody
    ): Call<CloudinaryUploadResponse>
}