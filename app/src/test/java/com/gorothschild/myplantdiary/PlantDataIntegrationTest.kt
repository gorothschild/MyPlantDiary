package com.gorothschild.myplantdiary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gorothschild.myplantdiary.dto.Plant
import com.gorothschild.myplantdiary.service.PlantService
import com.gorothschild.myplantdiary.ui.main.MainViewModel
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
class PlantDataIntegrationTest {
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
        givenAFeedOfPlantDataAreAvailable()
        whenSearchForRedbud()
        thenResultContainsEasternRedbud()
        // verify step is removed, verify is only for unit testing
    }


    private fun givenAFeedOfPlantDataAreAvailable() {
         mvm = MainViewModel()
        // integration test removes mock
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
            assertTrue(redbudFound)
        }
    }

    @Test
    fun searchForGarbage_returnsNothing() {
        givenAFeedOfPlantDataAreAvailable()
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