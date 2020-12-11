package mg.pulse.pointagecar.remote

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface PointageAPI {

    companion object Factory{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://cryptic-castle-42591.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @GET("api/cars")
    fun get(@Path("username") username: String?): Call<String>?

}