package com.example.movieapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.BuildConfig
import com.example.movieapp.models.PopularResponse
import com.example.movieapp.models.Results
import com.example.movieapp.retrofit.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {
    var listPopulars: MutableLiveData<List<Results>> = MutableLiveData()
    var listTopRated: MutableLiveData<List<Results>> = MutableLiveData()

    fun getPopularList(language: String, page: Int) {
        RetroInstance.instance.getPopularList(language, page, BuildConfig.API_KEY)
            .enqueue(object : Callback<PopularResponse> {
                override fun onResponse(
                    call: Call<PopularResponse>,
                    response: Response<PopularResponse>
                ) {
                    if (response.isSuccessful) {
                        val listPopular = response.body()?.results

                        listPopulars.postValue(listPopular)
                    } else {
                        val listPopular = response.body()

                        Log.d("Failed", "Failed: $listPopular")
                    }
                }

                override fun onFailure(call: Call<PopularResponse>, t: Throwable) {
                    listPopulars.postValue(null)

                    Log.d("API_CALL", "API call failed. Throwable: ${t.message}")
                }
            })
    }

    fun getTopRatedList(language: String, page: Int) {
        RetroInstance.instance.getTopRatedList(language, page, BuildConfig.API_KEY)
            .enqueue(object : Callback<PopularResponse> {
                override fun onResponse(
                    call: Call<PopularResponse>,
                    response: Response<PopularResponse>
                ) {
                    if (response.isSuccessful) {
                        val topRatedList = response.body()?.results

                        listTopRated.postValue(topRatedList)
                    } else {
                        val topRatedList = response.body()

                        Log.d("Failed", "Failed: $topRatedList")
                    }
                }

                override fun onFailure(call: Call<PopularResponse>, t: Throwable) {
                    listTopRated.postValue(null)

                    Log.d("API_CALL", "API call failed. Throwable: ${t.message}")
                }
            })
    }
}