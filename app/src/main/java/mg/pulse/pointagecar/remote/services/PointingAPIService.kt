package mg.pulse.pointagecar.remote.services

import mg.pulse.pointagecar.models.entities.Pointing

class PointingAPIService : BaseAPIService() {

    suspend fun getRamassagesByDate(idCar:String, dateRamassage:String): List<Pointing>{
        return pointageAPI?.getRamassagesByDateAndCar(idCar,dateRamassage)?: listOf()
    }

    suspend fun getLivraisonByDate(idCar:String, dateLivraison:String): List<Pointing>{
        return pointageAPI?.getLivraisonsByDateAndCar(idCar,dateLivraison)?: listOf()
    }

}