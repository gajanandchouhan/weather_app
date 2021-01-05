package com.gc.weatherapp.home

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gc.weatherapp.BuildConfig
import com.gc.weatherapp.MainActivity
import com.gc.weatherapp.cityweather.CityWeatherFragment
import com.gc.weatherapp.common.ItemClickListener
import com.gc.weatherapp.data.AddressInfo
import com.gc.weatherapp.databinding.FragmentHomeBinding
import com.sucho.placepicker.AddressData
import com.sucho.placepicker.Constants
import com.sucho.placepicker.MapType
import com.sucho.placepicker.PlacePicker

class HomeFragment : Fragment() {
    private lateinit var adapter: HomeAdapter
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var list: MutableList<AddressInfo>

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentHomeBinding.btnPlus.setOnClickListener { startPlacePicker() }
        setCityAdapter()
    }

    private fun setCityAdapter() {
        list = ArrayList()
        fragmentHomeBinding.rvCities.layoutManager = LinearLayoutManager(activity!!)
        adapter = HomeAdapter(list, object : ItemClickListener {
            override fun onItemClick(position: Int) {
                (activity as MainActivity).addFragment(CityWeatherFragment(list[position].city))
            }

        })
        fragmentHomeBinding.rvCities.adapter = adapter
    }

    fun startPlacePicker() {
        val applicationInfo = activity?.packageManager?.getApplicationInfo(
            BuildConfig.APPLICATION_ID,
            PackageManager.GET_META_DATA
        )
        val intent = PlacePicker.IntentBuilder()
            .setLatLong(17.3850, 78.4867)
            .showLatLong(true)
            .setMapType(MapType.NORMAL)
            .setPlaceSearchBar(
                true,
                applicationInfo?.metaData?.getString("com.google.android.geo.API_KEY")
            )
            .build(activity!!)
        startActivityForResult(intent, Constants.PLACE_PICKER_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.PLACE_PICKER_REQUEST && resultCode == Activity.RESULT_OK) {
            val addressData = data?.getParcelableExtra<AddressData>(Constants.ADDRESS_INTENT)
            if (addressData?.addressList != null && addressData.addressList!!.isNotEmpty()) {
                addNewCity(addressData)
            }

        }
    }

    private fun addNewCity(addressData: AddressData) {
        val latitude = addressData.latitude;
        val longitude = addressData.longitude;
        val city =
            if (addressData.addressList!![0].locality != null) addressData.addressList!![0].locality else "Unknown"
        list.add(AddressInfo(city, latitude, longitude))
        adapter.notifyItemInserted(list.size)
    }
}