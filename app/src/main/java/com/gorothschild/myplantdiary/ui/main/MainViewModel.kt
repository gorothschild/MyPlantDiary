package com.gorothschild.myplantdiary.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gorothschild.myplantdiary.dto.Plant
import com.gorothschild.myplantdiary.service.PlantService

class MainViewModel : ViewModel() {
    private var _plants: MutableLiveData<ArrayList<Plant>> = MutableLiveData<ArrayList<Plant>>()
    var plantService: PlantService = PlantService()

    init {
        fetchPlants("dummytext")
    }
    fun fetchPlants(plantName: String) {
        _plants = plantService.fetchPlants(plantName)
    }
    internal var plants:MutableLiveData<ArrayList<Plant>>
        get() {return _plants}
        set(value) {_plants = value}

}