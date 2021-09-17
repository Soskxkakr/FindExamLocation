package com.example.findexamlocation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findexamlocation.R
import com.example.findexamlocation.adapters.SearchAdapter
import com.example.findexamlocation.databinding.ActivitySearchBinding
import com.example.findexamlocation.models.Area
import com.example.findexamlocation.viewmodels.LocationViewModel

class SearchActivity : AppCompatActivity() {

    var listOfLocations = ArrayList<Area>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setUpListOfLocations()
        // Initializing the adapter for recycler view
        val rvAdapter = SearchAdapter(this, listOfLocations)
        val linearLayout = LinearLayoutManager(this)

        // Data binding utilities
        val binding : ActivitySearchBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_search)

        binding.lifecycleOwner = this
        binding.viewmodel = viewmodel

        binding.rvLocations.apply {
            adapter = rvAdapter
            layoutManager = linearLayout
        }

        // Observe = On Text Change,
        // Update to the list every action user made to the edit text
        viewmodel.inputLocationName.observe(this, Observer {
            if (it.isNullOrBlank()) {
                rvAdapter.filterList(listOfLocations)
            } else {
                var filteredList = ArrayList<Area>()
                for (area : Area in listOfLocations) {
                    if (area.name.lowercase().contains(it.lowercase())) {
                        filteredList.add(Area(area.name, area.lat, area.long))
                    }
                }
                rvAdapter.filterList(filteredList)
            }
        })
    }

    private val viewmodel: LocationViewModel by lazy {
        ViewModelProvider(this).get(LocationViewModel::class.java)
    }

    private fun setUpListOfLocations() {
        listOfLocations.add(Area("Exam Hall - Bukit Jalil", 3.0587, 101.6917))
        listOfLocations.add(Area("Exam Hall - Damansara", 3.0941, 101.5475))
        listOfLocations.add(Area("Exam Hall - Johor Bahru", 1.4927, 103.7414))
        listOfLocations.add(Area("Exam Hall - KL Sentral", 3.1343, 101.6863))
        listOfLocations.add(Area("Exam Hall - Malacca", 2.1896, 102.2501))
        listOfLocations.add(Area("Exam Hall - Pavilion", 3.1489, 101.7133))
        listOfLocations.add(Area("Exam Hall - Penang", 5.4164, 100.3327))
        listOfLocations.add(Area("Exam Hall - Selangor", 3.0738, 101.5183))
        listOfLocations.add(Area("Exam Hall - SS15", 3.0736, 101.5874))
        listOfLocations.add(Area("Exam Hall - Sunway", 3.0684, 101.6025))
    }
}