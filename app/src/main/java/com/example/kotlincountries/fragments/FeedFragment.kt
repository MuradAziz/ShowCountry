package com.example.kotlincountries.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlincountries.R
import com.example.kotlincountries.adapter.CountryAdapter
import com.example.kotlincountries.viewModel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {
   lateinit var viewModel: FeedViewModel
   private val adapter=CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        countryList.layoutManager=LinearLayoutManager(context)
        countryList.adapter=adapter

        swipeRefresh.setOnRefreshListener {
            countryList.visibility=View.GONE
            countryError.visibility=View.GONE
            viewModel.resfreshApi()
            swipeRefresh.isRefreshing=false
        }


        observeLivedata()


    }
    fun observeLivedata(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries->
            countries?.let {
                countryList.visibility=View.VISIBLE
                adapter.updateList(countries)
            }

        })
            viewModel.countryError.observe(viewLifecycleOwner, Observer{error->
                error?.let {
                    if(it){
                        countryError.visibility=View.VISIBLE
                    }else{
                        countryError.visibility=View.GONE
                    }
                }
            })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer{load->
            load?.let {
                if(it){
                        countryLoading.visibility=View.VISIBLE
                        countryError.visibility=View.GONE
                    countryList.visibility=View.GONE
                }else{
                    countryLoading.visibility=View.GONE
                }
            }

        })
    }
}