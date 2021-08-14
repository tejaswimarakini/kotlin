package de.conti.r2d2.kotlindemo.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import de.conti.r2d2.kotlindemo.R
import de.conti.r2d2.kotlindemo.databinding.BottomSheetDialogBinding
import de.conti.r2d2.kotlindemo.viewmodel.WeatherViewModel


class BottomSheetDialog : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetDialogBinding
    lateinit var viewModel: WeatherViewModel
    var cityValue: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = BottomSheetDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        binding.btnAddNewCity.setOnClickListener {
            cityValue = binding.cityInputView.text.toString()
            cityValue?.let {
                if(it.isNotEmpty()) {
                    viewModel.getWeatherForAddedCity(it)
                } else {
                    Toast.makeText(activity, "Please enter a valid city name", Toast.LENGTH_LONG).show()
                }
            }
            dismiss()
        }



        return view
    }

}