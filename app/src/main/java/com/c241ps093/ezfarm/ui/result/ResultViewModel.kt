package com.c241ps093.ezfarm.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c241ps093.ezfarm.data.entity.IdentificationResponse
import com.c241ps093.ezfarm.data.repository.EzFarmRepository
import okhttp3.MultipartBody
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

    fun postData(imageFile : MultipartBody.Part){
        _isLoading.postValue(true)
        val client = ezFarmRepository.uploadImage(imageFile)

        client.enqueue(object : Callback<IdentificationResponse> {
            override fun onResponse(
                p0: Call<IdentificationResponse>,
                p1: Response<IdentificationResponse>
            ) {
                val responseBody = p1.body()
                if(p1.isSuccessful && responseBody !== null){
                    _identificationData.postValue(responseBody)
                } else {
                    _errorMessage.postValue("Image Unidentifiable")
                }
            }

            override fun onFailure(p0: Call<IdentificationResponse>, p1: Throwable) {
                _errorMessage.postValue("Network error")
            }
        })

    }
}
