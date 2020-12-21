package mg.pulse.pointagecar.remote.services

import mg.pulse.pointagecar.models.entities.User

class UserAPIService: BaseAPIService() {

    suspend fun findUserByMatricule(matricule:String): User?{
        return pointingAPI?.findUserByMatricule(matricule)
    }

}