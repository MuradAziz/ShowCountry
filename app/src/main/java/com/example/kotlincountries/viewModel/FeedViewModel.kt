package com.example.kotlincountries.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountries.model.Country
import com.example.kotlincountries.service.CountryApiService
import com.example.kotlincountries.service.CountryDatabase
import com.example.kotlincountries.util.CustomShared
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countryApiService= CountryApiService()
    private val disposables= CompositeDisposable()
    private val customShared=CustomShared(getApplication())
    private var refreshTime=10*60*1000*1000*1000L

    val countries=MutableLiveData<List<Country>>()
    val countryError=MutableLiveData<Boolean>()
    val countryLoading=MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime=customShared.getTime()
        if(updateTime !=null && updateTime!=0L && System.nanoTime()-updateTime<refreshTime){
            getDataFromSql()
        }else {
            getDataFromApi()
        }
    }

    private fun getDataFromSql() {
        launch {
        val countriesSql = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countriesSql)
            Toast.makeText(getApplication(), "SQl", Toast.LENGTH_LONG).show()

    }
    }
     fun resfreshApi(){
        getDataFromApi()
    }
    private fun getDataFromApi(){
        countryLoading.value=true
        disposables.add(
            countryApiService.getdata()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                    storeInSqlite(t)
                        Toast.makeText(getApplication(), "Api", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        countryError.value=true
                        countryLoading.value=false
                        e.printStackTrace()
                    }

                })

        )
    }
    private fun showCountries(countryList: List<Country>){
        countries.value=countryList
        countryLoading.value=false
        countryError.value=false
    }
    private fun storeInSqlite(list:List<Country>){
launch {
    val dao=CountryDatabase(getApplication()).countryDao()
    dao.DeleteAll()
    val listLong=dao.insertAll(*list.toTypedArray())
    var i=0
    while( i<list.size){
        list[i].uuid=listLong[i].toInt()
        i=i+1
    }
    showCountries(list)

}

        customShared.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }



}