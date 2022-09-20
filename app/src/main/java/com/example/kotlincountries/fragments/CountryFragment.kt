package com.example.kotlincountries.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlincountries.R
import com.example.kotlincountries.util.downloadUrl
import com.example.kotlincountries.util.placeHolderDrawble
import com.example.kotlincountries.viewModel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*


class CountryFragment : Fragment() {
    private lateinit var countryViewModel: CountryViewModel
    private var countryUuid=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid=CountryFragmentArgs.fromBundle(it).uuid
        }

        countryViewModel= ViewModelProvider(this)[CountryViewModel::class.java]
        countryViewModel.getDataFromRoom(countryUuid)
        observeLivedata()



    }

    private fun observeLivedata(){
        countryViewModel.countryLivedata.observe(viewLifecycleOwner, Observer {country->
            country?.let {
                countryName.text=country.countryName
                countryCapital.text=country.countryCapital
                countryLanguage.text=country.countryLanguage
                countryRegion.text=country.countryRegion
                countryCurrency.text=country.countryCurrency
                context?.let {
                    country.imageUrl?.let { it1 -> countryImage.downloadUrl(it1, placeHolderDrawble(it)) }
                }
            }
        })
    }


}