package com.gorothschild.myplantdiary.service

import androidx.lifecycle.MutableLiveData
import com.gorothschild.myplantdiary.dto.Plant

class PlantService {
    fun fetchPlants(plantName: String) : MutableLiveData<ArrayList<Plant>> {
        return MutableLiveData<ArrayList<Plant>>()
    }
}