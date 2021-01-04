package mg.pulse.pointagecar.remote.services

import mg.pulse.pointagecar.models.entities.User
import mg.pulse.pointagecar.remote.models.AuthRequest
import mg.pulse.pointagecar.remote.models.AuthResponse

class UserAPIService: BaseAPIService() {

    suspend fun authentificate(auth: AuthRequest):AuthResponse?{
        return pointingAPI?.authentificate(auth)
    }

    suspend fun findUserByMatricule(matricule:String): User?{
        return pointingAPI?.findUserByMatricule(matricule)
    }

}