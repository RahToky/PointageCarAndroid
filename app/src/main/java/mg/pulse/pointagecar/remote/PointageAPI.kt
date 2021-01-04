package mg.pulse.pointagecar.remote

import mg.pulse.pointagecar.models.entities.Pointing
import mg.pulse.pointagecar.models.entities.User
import mg.pulse.pointagecar.remote.models.AuthRequest
import mg.pulse.pointagecar.remote.models.AuthResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


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

    // POINTINGS

    @POST("auth/signin")
    suspend fun authentificate(@Body auth:AuthRequest):AuthResponse?

    @GET("api/pickup-pointing/dateandcar")
    suspend fun findRamassagesByDateAndCar(@Query("idcar") idCar:String, @Query("date") date:String): List<Pointing>

    @POST("api/pickup-pointing/save")
    suspend fun savePickupPointing(@Body pointing:Pointing)

    @GET("api/delivery-pointing/dateandcar")
    suspend fun findLivraisonsByDateAndCar(@Query("idcar") idCar:String, @Query("date") date:String): List<Pointing>

    @POST("api/delivery-pointing/save")
    suspend fun saveDeliveryPointing( pointing:Pointing)

    // USERS

    @GET("api/user/matricule")
    suspend fun findUserByMatricule(@Query("matricule") matricule:String): User

}