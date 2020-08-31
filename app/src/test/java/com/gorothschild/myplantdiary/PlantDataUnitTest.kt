package com.gorothschild.myplantdiary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.gorothschild.myplantdiary.dto.Plant
import com.gorothschild.myplantdiary.service.PlantService
import com.gorothschild.myplantdiary.ui.main.MainViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PlantDataUnitTest {
    // this makes tests "observables"
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule() // added in gradle test
    // testImplementation 'androidx.arch.core:core-testing:2.1.0' and
    // androidTestImplementation 'androidx.arch.core:core-testing:2.1.0' (for eventual androidTest

    lateinit var mvm: MainViewModel

    var plantService = mockk<PlantService>()

    @Test
    fun confirmEasternRedbud_outputsEasternRedbud() {
        var plant = Plant("Cercis", "canadesis", "Eastern Redbud")
        assertEquals("Eastern Redbud", plant.toString())
    }

    @Test
    fun searchForRedbud_returnsRedbud() {
        givenAFeedOfMockedPlantDataAreAvailable()
        whenSearchForRedbud()
        thenResultContainsEasternRedbud()
    }

    private fun givenAFeedOfMockedPlantDataAreAvailable() {
         mvm = MainViewModel()
        createMockData()
    }

    private fun createMockData() {
        var allPlantsLiveData = MutableLiveData<ArrayList<Plant>>()
        var allPlants = ArrayList<Plant>()
        // create and add plants that I want to test
        var redbud = Plant("Cercis", "canadenis", "Eastern Redbud")
        allPlants.add(redbud)
        var redOak = Plant("Quercus", "rubra", "Red Oak")
        allPlants.add(redOak)
        var whiteOak = Plant("Quercus", "rubra", "White Oak")
        allPlants.add(whiteOak)
        allPlantsLiveData.postValue(allPlants)
        // this is what returns the data request
        // this is the original
        // every { plantService.fetchPlants(any<String>()) } returns allPlantsLiveData // can hardcode string ex "Redbud"
        // *** circleci failed because "any" java.lang.AssertionError: expected:<0> but was:<3> ***

        // can make conditional in this ex: if red bud return only red bud etc..
        every { plantService.fetchPlants(or("Redbud", "Quercus")) } returns allPlantsLiveData
        // NOTE: *** can chain these above if string is ... then do above, if NOT then return list with 0 items
        every { plantService.fetchPlants(not (or("Redbud", "Quercus"))) } returns MutableLiveData<ArrayList<Plant>>()
        // now if requesting junk, it will return nothing, as junk should behave
        mvm.plantService = plantService // on right is the mock above so it returns
    }

    private fun whenSearchForRedbud() {
        mvm.fetchPlants("Redbud")
    }

    private fun thenResultContainsEasternRedbud() {
        var redbudFound = false
        mvm.plants.observeForever{
            // here is where we do the observing (this implies plants is 'live data'
            assertNotNull(it)
            assertTrue(it.size > 0)
            it.forEach {
                // it is a 'different val, its the value in the inner loop
                if (it.genus == "Cercis" && it.species == "canadenis" && it.common.contains("Eastern Redbud")) {
                    redbudFound = true
                }
            }
        }
        assertTrue(redbudFound)
    }

    @Test
    fun searchForGarbage_returnsNothing() {
        givenAFeedOfMockedPlantDataAreAvailable()
        whenISearchForGarbage()
        thenIGetZeroResults()
    }

    private fun whenISearchForGarbage() {
        mvm.fetchPlants("gobblydegoop")
    }

    private fun thenIGetZeroResults() {
         mvm.plants.observeForever {
             assertEquals(0, it.size)
         }
    }
}