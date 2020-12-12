package mg.pulse.pointagecar.remote.services

import androidx.lifecycle.LiveData
import mg.pulse.pointagecar.models.entities.Collaborateur
import mg.pulse.pointagecar.remote.PointageAPI
import retrofit2.Call

class CollaboAPIRepository {

    private val pointageAPI:PointageAPI? = PointageAPI.getInstance()

    suspend fun getCollaborateurs():List<Collaborateur> = pointageAPI?.getCollaborateurs()?: listOf()

}