package com.gorothschild.myplantdiary.service

import androidx.lifecycle.MutableLiveData
import com.gorothschild.myplantdiary.RetrofitClientInstance
import com.gorothschild.myplantdiary.dao.IPlantDAO
import com.gorothschild.myplantdiary.dto.Plant
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class PlantService {
    fun fetchPlants(plantName: String) : MutableLiveData<ArrayList<Plant>> {
        var _plants = MutableLiveData<ArrayList<Plant>>()
        val service = RetrofitClientInstance.retrofitInstance?.create(IPlantDAO::class.java)
        val call = service?.getAllPlants() // returns a call object this will be background call
        call?.enqueue(object: Callback, retrofit2.Callback<ArrayList<Plant>> {
            override fun onResponse(
                call: Call<ArrayList<Plant>>,
                response: Response<ArrayList<Plant>>
            ) {
                _plants.value = response.body()
            }
            override fun onFailure(call: Call<ArrayList<Plant>>, t: Throwable) {
                var breakpointHere = 1+1
            }
        })
        return _plants
    }
}