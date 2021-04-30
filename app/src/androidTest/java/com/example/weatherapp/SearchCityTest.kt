package com.example.weatherapp

import androidx.test.espresso.action.GeneralLocation
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.weatherapp.presentation.MainActivity
import com.example.weatherapp.screen.DetailScreen
import com.example.weatherapp.screen.NearListScreen
import com.example.weatherapp.screen.SearchScreen
import org.junit.Rule
import org.junit.Test

class SearchCityTest : KTestCase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkCitySearch() {
        run {
            step("Navigate to search screen") {
                NearListScreen {
                    bottomNavigationMenu {
                        click(GeneralLocation.BOTTOM_RIGHT)
                    }
                }
            }

            step("Write city name and click submit button") {
                SearchScreen {
                    cityNameEditField {
                        typeText("New York")
                    }

                    submitButton {
                        click()
                    }
                }
            }

            step("Detail screen has properly city information") {
                DetailScreen {
                    cityInfo {
                        hasText("New York US")
                    }
                }
            }
        }
    }
}