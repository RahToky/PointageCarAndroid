package mg.pulse.pointagecar.remote

import androidx.lifecycle.LiveData
import mg.pulse.pointagecar.models.entities.Collaborateur
import mg.pulse.pointagecar.models.entities.Ramassage
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface PointageAPI {

    companion object Factory{

        private var retrofitInstance:PointageAPI? = null
        const val BASE_URL:String = "https://cryptic-castle-42591.herokuapp.com/"
        private val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun getInstance():PointageAPI?{
            if(retrofitInstance == null) {
                retrofitInstance = retrofit.create(PointageAPI::class.java)
            }
            return retrofitInstance
        }
    }

    @GET("api/collaborateurs")
    suspend fun getCollaborateurs(): List<Collaborateur>

    @GET("api/ramassages")
    suspend fun getRamassages(): List<Ramassage>

    @GET("api/ramassages/dateandcar?date={date}&idcar={idCar}")
    suspend fun getRamassagesByDateAndCar(@Path("idCar") idCar:String, @Path("date") date:String): List<Ramassage>

}