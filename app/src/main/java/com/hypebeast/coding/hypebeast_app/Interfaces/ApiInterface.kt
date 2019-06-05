package com.hypebeast.coding.hypebeast_app.Interfaces

import com.hypebeast.coding.hypebeast_app.Model.DataModel
import retrofit2.Call
import retrofit2.http.GET


interface ApiInterface {
    @GET("api/v1/employees")
    fun getProjectList(): Call<List<DataModel>>


}