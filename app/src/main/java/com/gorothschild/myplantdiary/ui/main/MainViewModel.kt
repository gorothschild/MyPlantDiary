package com.gorothschild.myplantdiary.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gorothschild.myplantdiary.dto.Plant
import com.gorothschild.myplantdiary.service.PlantService

class MainViewModel : ViewModel() {
    var plants: MutableLiveData<ArrayList<Plant>> = MutableLiveData<ArrayList<Plant>>()
    var plantService: PlantService = PlantService()

    init {
        fetchPlants("dummytext")
    }
    fun fetchPlants(plantName: String) {
        plants = plantService.fetchPlants(plantName)
    }

}