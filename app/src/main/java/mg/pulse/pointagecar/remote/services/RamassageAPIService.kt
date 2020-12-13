package mg.pulse.pointagecar.remote.services

import mg.pulse.pointagecar.models.entities.Collaborateur
import mg.pulse.pointagecar.models.entities.Ramassage

class RamassageAPIService : BaseAPIService() {

    suspend fun getRamassagesByIdCar(idCar: String): List<Ramassage> =
        pointageAPI?.getRamassages()?.filter { it.car.id == idCar } ?: listOf()

    fun getCollaborateurs(ramassageList:List<Ramassage>? = listOf()):MutableList<Collaborateur>{
        var collaboList:MutableList<Collaborateur> = mutableListOf()
        for((i,ramassage) in ramassageList?.withIndex()!!){
            collaboList[i] = ramassage.collaborateur
        }
        return collaboList
    }
}