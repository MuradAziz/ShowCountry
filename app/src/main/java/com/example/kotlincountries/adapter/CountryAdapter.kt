package com.example.kotlincountries.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

import com.example.kotlincountries.R
import com.example.kotlincountries.databinding.RowItemBinding
import com.example.kotlincountries.fragments.FeedFragmentDirections
import com.example.kotlincountries.model.Country
import com.example.kotlincountries.util.downloadUrl
import com.example.kotlincountries.util.placeHolderDrawble
import kotlinx.android.synthetic.main.row_item.view.*

class CountryAdapter(private val countryList: ArrayList<Country>):
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), CountryListener {
    class CountryViewHolder(val view: RowItemBinding): RecyclerView.ViewHolder(view.root) {


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater=LayoutInflater.from(parent.context)
       // val view=inflater.inflate(R.layout.row_item, parent, false)
        val view=DataBindingUtil.inflate<RowItemBinding>(inflater, R.layout.row_item, parent,false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.country=countryList[position]
        holder.view.listener=this


//holder.view.name.text=countryList[position].countryName
//        holder.view.region.text=countryList[position].countryRegion
//
//        holder.view.setOnClickListener{
//            val action=FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
//            Navigation.findNavController(it).navigate(action)
//        }
//        countryList[position].imageUrl?.let { holder.view.imageView.downloadUrl(it, placeHolderDrawble(holder.view.context)) }
//

    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateList(newCountryList:List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onClickedCountry(v: View) {
        val uuid=v.CountryUuidText.text.toString().toInt()
        val action=FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
           Navigation.findNavController(v).navigate(action)
        }

}