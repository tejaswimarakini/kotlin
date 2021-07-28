package de.conti.r2d2.kotlindemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import de.conti.r2d2.kotlindemo.R
import de.conti.r2d2.kotlindemo.model.WeatherResponse
import java.util.ArrayList

class WeatherListAdapter(private val weatherDataList: ArrayList<WeatherResponse>) :
    RecyclerView.Adapter<WeatherListAdapter.ViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(position: Int, weatherResponse: WeatherResponse)
    }

    lateinit var mClickListener: OnItemClickListener

    fun setOnItemClickListener(aClickListener: OnItemClickListener) {
        mClickListener = aClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.city_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = weatherDataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cityNameView.text = weatherDataList.get(position).name
        holder.countryNameView.text = getCountryName(weatherDataList.get(position).sys.country)
        holder.rowLayout.setOnClickListener {
            mClickListener.onItemClick(position, weatherDataList.get(position))
        }
    }

    private fun getCountryName(countryCode: String): String {
        return when (countryCode) {
            "IN" -> "India"
            "GB" -> "United Kingdom"
            "US" -> "United States"
            "UAE" -> "United Arab Emirates"
            else -> "UnIdentified"
        }

        /*var countryname: String
        when (countryCode) {
            "IN" -> countryname = "India"
            "GB" -> countryname = "United Kingdom"
            "US" -> countryname = "United States"
            "UAE" -> countryname = "United Arab Emirates"
            else -> countryname = "UnIdentified"
        }
        return countryname*/

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityNameView: TextView = view.findViewById(R.id.cityNameTxtView)
        val countryNameView: TextView = view.findViewById(R.id.countryNameView)
        val rowLayout: ConstraintLayout = view.findViewById(R.id.rowLayout)
    }
}