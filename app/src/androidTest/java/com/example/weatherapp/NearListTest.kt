package com.example.weatherapp

import android.Manifest
import android.location.Location
import androidx.test.espresso.action.GeneralLocation
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.example.weatherapp.data.location.WeatherAppLocationService
import com.example.weatherapp.data.network.City
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.presentation.MainActivity
import com.example.weatherapp.screen.DetailScreen
import com.example.weatherapp.screen.FavoriteListScreen
import com.example.weatherapp.screen.NearListScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sqrt


@RunWith(AndroidJUnit4::class)
class NearListTest : KTestCase() {


    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var weatherAppLocationService: WeatherAppLocationService

    private val weatherApiService = WeatherApi

    private lateinit var cities: List<City>
    private lateinit var citiesForTest: List<CityTest>

    private lateinit var location: Location

    @Before
    fun init() {
        activityRule.scenario.onActivity { activity ->
            weatherAppLocationService = WeatherAppLocationService(activity, activity)
        }


        runBlocking {
            weatherAppLocationService.getLastLocation {
                if (it != null) {
                    location = it
                }
            }

            delay(500L)

            cities = weatherApiService
                .retrofitService
                .getCityList(location.latitude, location.longitude)
                .list

        }

        var distance: Int
        citiesForTest = cities.map { city ->
            distance = calculateDistance(
                location.latitude,
                location.longitude,
                city.coordinate.latitude,
                city.coordinate.longitude
            ).toInt()
            CityTest(
                cityName = city.name,
                countryName = city.country.name,
                distance = "${if (distance > 1) "$distance kilometers" else "Less than one kilometre"} from you"
            )
        }
    }


    @Test
    fun checkNearCitiesListDisplayed() {
        run {
            step("Displayed correct number of cities") {
                NearListScreen {
                    citiesList {
                        assert(getSize() == 45)
                    }
                }
            }
            step("All items are displayed properly") {
                NearListScreen {
                    citiesList {
                        checkCities(
                            citiesForTest
                        )
                    }
                }
            }
        }
    }

    @Test
    fun checkTransitionToDetailScreen() {
        run {
            step("Try to click on item") {
                NearListScreen {
                    citiesList {
                        scrollTo(10)
                        childAt<NearListScreen.CityItem>(10) {
                            isVisible()
                            isClickable()
                            click()
                        }
                    }
                }
            }

            step("Detail screen has correctly city information") {
                DetailScreen {
                    cityInfo {
                        isDisplayed()
//                        hasText(getResourceString(R.string.city_and_country_name_format, citiesForTest[10].cityName, citiesForTest[10].countryName))
                        hasText("${citiesForTest[10].cityName} ${citiesForTest[10].countryName}")
                    }
                }
            }
        }
    }

    @Test
    fun checkMakeCityFavoriteOption() {
        val checkIndex = 2
        run {
            step("Add city to favorite") {
                NearListScreen {
                    citiesList {
                        scrollTo(checkIndex)
                        childAt<NearListScreen.CityItem>(checkIndex) {
                            isVisible()
                            isClickable()
                            click()
                        }
                    }
                }

                DetailScreen {
                    isFavoriteImage {
                        click()
                    }

                    navigateUpButton {
                        click()
                    }
                }
            }

            step("Is there city in favorites") {
                NearListScreen {
                    bottomNavigationMenu {
                        click(GeneralLocation.CENTER)
                    }
                }

                FavoriteListScreen {
                    favoriteCitiesList {
                        childAt<FavoriteListScreen.FavoriteCityItem>(0) {
                            isVisible()
                            isClickable()

                            cityName {
                                hasText(citiesForTest[checkIndex].cityName)
                            }

                            click()
                        }
                    }
                }
            }

            step("Delete city from favorite") {
                DetailScreen {
                    isFavoriteImage {
                        click()
                    }
                }
            }
        }
    }

    class CityTest(val cityName: String, val countryName: String, val distance: String)


    private fun checkCities(citiesTests: List<CityTest>) {
        citiesTests.forEachIndexed { index, city ->
            NearListScreen {
                citiesList {
                    childAt<NearListScreen.CityItem>(index) {
                        cityName {
                            isDisplayed()
                            hasText(city.cityName)
                        }

                        countryName {
                            isDisplayed()
                            hasText(city.countryName)
                        }

                        distance {
                            isDisplayed()
                            hasText(city.distance)
                        }
                    }
                }
            }
        }
    }

    private fun calculateDistance(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val p = 0.017453292519943295    // Math.PI / 180
        val a = 0.5 - cos((lat2 - lat1) * p) / 2 +
                cos(lat1 * p) * cos(lat2 * p) *
                (1 - cos((lon2 - lon1) * p)) / 2

        return 12742 * asin(sqrt(a)) // 2 * R; R = 6371 km
    }

}


