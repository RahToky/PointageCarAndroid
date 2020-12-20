package mg.pulse.pointagecar.remote.services

import mg.pulse.pointagecar.models.entities.Pointing
import mg.pulse.pointagecar.models.entities.User
import mg.pulse.pointagecar.remote.PointageAPI

class UserAPIService: BaseAPIService() {

    suspend fun findUserByMatricule(matricule:String): User?{
        return pointageAPI?.findUserByMatricule(matricule)
    }

}