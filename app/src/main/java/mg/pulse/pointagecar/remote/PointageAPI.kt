package mg.pulse.pointagecar.remote

import mg.pulse.pointagecar.models.entities.Car
import mg.pulse.pointagecar.models.entities.Pointing
import mg.pulse.pointagecar.models.entities.User
import mg.pulse.pointagecar.remote.models.AuthRequest
import mg.pulse.pointagecar.remote.models.AuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface PointageAPI {
    companion object Factory{
        private var pointageAPI:PointageAPI? = null

        fun getInstance(token:String?):PointageAPI?{
            if(pointageAPI == null) {
                pointageAPI = getPointageAPIInstance(token)
            }else{
                if(token?.let { RetrofitFactory.token?.compareTo(it,false) } != 0){
                    pointageAPI = getPointageAPIInstance(token)
                }
            }
            return pointageAPI
        }

        private fun  getPointageAPIInstance(token:String?) = RetrofitFactory.getInstance(token).create(PointageAPI::class.java)
    }

    // POINTINGS

    @POST("auth/signin")
    suspend fun authentificate(@Body auth:AuthRequest):AuthResponse?

    @GET("api/pickup-pointing/dateandcar")
    suspend fun findPickupPointingByDateAndCar(@Query("idcar") idCar:String, @Query("date") date:String): List<Pointing>

    @POST("api/pickup-pointing/save")
    suspend fun savePickupPointing(@Body pointing:Pointing)

    @GET("api/delivery-pointing/dateandcar")
    suspend fun findDeliveryPointingByDateAndCar(@Query("idcar") idCar:String, @Query("date") date:String): List<Pointing>

    @POST("api/delivery-pointing/save")
    suspend fun saveDeliveryPointing( pointing:Pointing)

    // USERS

    @GET("api/user/matricule")
    suspend fun findUserByMatricule(@Query("matricule") matricule:String): User


    // CARS
    @GET("api/car/id")
    suspend fun findCarById(@Query("id") id:String): Car


}