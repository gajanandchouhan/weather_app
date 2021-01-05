package com.gc.weatherapp.cityweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gc.weatherapp.R
import com.gc.weatherapp.common.DataWrapper
import com.gc.weatherapp.common.ErrorType
import com.gc.weatherapp.data.CurrentWeatherResponse
import com.gc.weatherapp.databinding.FragmentCityWeatherBinding
import com.gc.weatherapp.di.cityWeatherFragmentModule
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class CityWeatherFragment(private val city: String) : Fragment() {
    private lateinit var fragmentCityWeatherBinding: FragmentCityWeatherBinding
    val cityWeatherViewModel: CityWeatherViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCityWeatherBinding = FragmentCityWeatherBinding.inflate(inflater, container, false)
        loadKoinModules(cityWeatherFragmentModule)
        return fragmentCityWeatherBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoader()
        getCityWeatherinfo()
    }

    private fun initLoader() {
        cityWeatherViewModel.isLoading().observe(this, Observer {
            fragmentCityWeatherBinding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun getCityWeatherinfo() {
        cityWeatherViewModel.getCurrentWeather(city).observe(this, Observer {
            val dataWrapper = it as DataWrapper
            if (dataWrapper.response != null) {
                val currentWeatherResponse = dataWrapper.response as CurrentWeatherResponse
                if (currentWeatherResponse.cod == "200") {
                    fragmentCityWeatherBinding.layoutWeatherInfo.visibility = View.VISIBLE
                    fragmentCityWeatherBinding.weatherData = currentWeatherResponse
                } else {
                    fragmentCityWeatherBinding.tvErrorMessage.visibility = View.VISIBLE
                    fragmentCityWeatherBinding.tvErrorMessage.text = currentWeatherResponse.message
                }
            } else if (dataWrapper.errorType == ErrorType.No_INTERNET) {
                fragmentCityWeatherBinding.layoutWeatherInfo.visibility = View.GONE
                fragmentCityWeatherBinding.tvErrorMessage.visibility = View.VISIBLE
                fragmentCityWeatherBinding.tvErrorMessage.text = getString(R.string.no_internet)
            } else if (dataWrapper.errorType == ErrorType.NOT_FOUND) {
                fragmentCityWeatherBinding.layoutWeatherInfo.visibility = View.GONE
                fragmentCityWeatherBinding.tvErrorMessage.visibility = View.VISIBLE
                fragmentCityWeatherBinding.tvErrorMessage.text = getString(R.string.city_not_fund)
            } else {
                fragmentCityWeatherBinding.layoutWeatherInfo.visibility = View.GONE
                fragmentCityWeatherBinding.tvErrorMessage.visibility = View.VISIBLE
                fragmentCityWeatherBinding.tvErrorMessage.text = getString(R.string.unknown_error)
            }
        })
    }
}