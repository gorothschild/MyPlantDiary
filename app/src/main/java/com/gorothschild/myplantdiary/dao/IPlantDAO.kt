package com.gorothschild.myplantdiary.dao

import com.gorothschild.myplantdiary.dto.Plant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IPlantDAO {
    @GET("/perl/mobile/viewplantsjsonarray.pl") // endpoint goes here
    fun getAllPlants(): Call<ArrayList<Plant>> //this is retrofit

    @GET("/perl/mobile/viewplantsjsonarray.pl")
    fun getPlants(@Query("Combined_Name") plantName: String): Call<ArrayList<Plant>>
    // The @Query appends ?Combined_Name= and adds var plantName
}