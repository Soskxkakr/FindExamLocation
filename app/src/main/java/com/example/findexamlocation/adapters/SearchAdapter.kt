package com.example.findexamlocation.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findexamlocation.R
import com.example.findexamlocation.models.Area
import com.example.findexamlocation.ui.MapsActivity
import com.example.findexamlocation.utils.Constants

class SearchAdapter(
    private val context : Context,
    private var listOfAreas : ArrayList<Area>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.location_list, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = listOfAreas[position]
        if (holder is MyViewHolder) {
            holder.location.text = model.name

            holder.itemView.setOnClickListener {
                val intent = Intent(context, MapsActivity::class.java)
                val activity = context as Activity

                intent.putExtra(Constants.EXTRA_LOCATION, model)
                activity.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfAreas.size
    }

    fun filterList(filteredArea : ArrayList<Area>) {
        listOfAreas = filteredArea
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        var location : TextView
        init {
            location = itemView.findViewById(R.id.tv_location)
        }
    }

}