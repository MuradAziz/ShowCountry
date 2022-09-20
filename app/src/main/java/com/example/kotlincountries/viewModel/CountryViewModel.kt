package com.example.kotlincountries.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.kotlincountries.model.Country
import com.example.kotlincountries.service.CountryApiService
import com.example.kotlincountries.service.CountryDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.util.*

class CountryViewModel(application: Application): BaseViewModel(application) {
    val countryLivedata=MutableLiveData<Country>()

    fun getDataFromRoom(uuid: Int) {
    launch {
        val dao=CountryDatabase(getApplication()).countryDao()
        val country=dao.getCountry(uuid)
        countryLivedata.value=country
    }

    }
}