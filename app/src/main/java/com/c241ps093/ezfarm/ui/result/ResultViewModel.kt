package com.c241ps093.ezfarm.ui.result

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c241ps093.ezfarm.data.entity.IdentificationResponse
import com.c241ps093.ezfarm.data.repository.EzFarmRepository
import com.c241ps093.ezfarm.reduceFileImage
import com.c241ps093.ezfarm.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultViewModel(private val ezFarmRepository: EzFarmRepository) : ViewModel() {
    private val _identificationData = MutableLiveData<IdentificationResponse?>()
    val identificationData : LiveData<IdentificationResponse?> = _identificationData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage

    fun postData(uriString : String, context: Context){
        _isLoading.postValue(true)
        val uri = Uri.parse(uriString)
        val imageFile = uriToFile(uri, context).reduceFileImage()
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )
        val client = ezFarmRepository.uploadImage(file = multipartBody)

        client.enqueue(object : Callback<IdentificationResponse> {
            override fun onResponse(
                p0: Call<IdentificationResponse>,
                p1: Response<IdentificationResponse>
            ) {
                _isLoading.postValue(false)
                val responseBody = p1.body()
                if(p1.isSuccessful && responseBody !== null){
                    _identificationData.postValue(responseBody)
                } else {
                    Log.d("CHECK", p1.errorBody().toString())
                    _errorMessage.postValue("Image Unidentifiable, Please Try Again")
                }
            }

            override fun onFailure(p0: Call<IdentificationResponse>, p1: Throwable) {
                _isLoading.postValue(false)
                _errorMessage.postValue("Network error")
            }
        })

    }
}
