package com.blez.trendinggithubrepos.data.API

import com.blez.trendinggithubrepos.data.NewsData
import com.blez.trendinggithubrepos.data.SearchNewsData
import retrofit2.http.GET
import retrofit2.http.Query

interface NewAPI {
    @GET("v2/top-headlines/")
  suspend  fun tendingNews(@Query("country") country : String,@Query("apiKey") apiKey : String,@Query("page") page : Int,@Query("pageSize") pageSize : Int =20) : NewsData
    @GET("/v2/everything/")
    suspend fun SearchNews(@Query("q") query : String,@Query("apiKey") apiKey : String,@Query("pageSize") pageSize : Int =20 ,@Query("page") page : Int) : NewsData


}