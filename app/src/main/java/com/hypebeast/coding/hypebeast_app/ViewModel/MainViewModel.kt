package com.hypebeast.coding.hypebeast_app.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.hypebeast.coding.hypebeast_app.Model.DataModel
import com.hypebeast.coding.hypebeast_app.Clients.APIClient
import com.hypebeast.coding.hypebeast_app.adapter.CustomAdapter
import com.hypebeast.coding.hypebeast_app.Interfaces.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var dataList: List<DataModel> = ArrayList()
    private val mutablePostList: MutableLiveData<List<DataModel>> = MutableLiveData()

    fun getProjectList(): LiveData<List<DataModel>>? {

        var apiServices = APIClient.client.create(ApiInterface::class.java)
        val call = apiServices.getProjectList()

        call.enqueue(object : Callback<List<DataModel>> {
            override fun onResponse(call: Call<List<DataModel>>, response: Response<List<DataModel>>) {

                val jsonResponse = response.body()
                if (jsonResponse != null) {
                    dataList = jsonResponse
                    mutablePostList.postValue(dataList)
                }else{
                    Log.d("TAG","dataList = null")
                }

            }

            override fun onFailure(call: Call<List<DataModel>>?, t: Throwable?) {
                Log.d("ERROR : ", t.toString())

            }
        })

        return mutablePostList
    }

    fun filterList(term: String, adapter: CustomAdapter) {
        if (term != "") {
            val list = adapter.originalList.filter { it.employee_name.contains(term, true) }
            adapter.filterList = list
            adapter.notifyDataSetChanged()
            Log.d("filterList : ", list.toString())

        } else {
            adapter.filterList = adapter.originalList
            adapter.notifyDataSetChanged()
        }

    }

    fun getOnQueryTextChange(adapter: CustomAdapter) : SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener{
        override fun onQueryTextChange(term: String?): Boolean {
            if (term != null) { filterList(term, adapter) }
            return false
        }
        override fun onQueryTextSubmit(term: String?): Boolean {
            if (term != null) { filterList(term, adapter) }
            return false
        }
    }

}