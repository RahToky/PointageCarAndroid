package mg.pulse.pointagecar.remote.services

import mg.pulse.pointagecar.models.entities.User
import mg.pulse.pointagecar.remote.PointageAPI
import mg.pulse.pointagecar.remote.models.AuthRequest
import mg.pulse.pointagecar.remote.models.AuthResponse

class UserAPIService: BaseAPIService() {

    suspend fun authentificate(auth: AuthRequest):AuthResponse?{
        val authResponse:AuthResponse? = PointageAPI.getInstance(null)?.authentificate(auth)
        token = authResponse?.data?.token
        return authResponse
    }

    suspend fun findUserByMatricule(matricule:String): User?{
        return  PointageAPI.getInstance(token)?.findUserByMatricule(matricule)
    }

}