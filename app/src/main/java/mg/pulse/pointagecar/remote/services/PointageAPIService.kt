package mg.pulse.pointagecar.remote.services

import mg.pulse.pointagecar.models.entities.Ramassage

class PointageAPIService : BaseAPIService() {

    suspend fun getRamassagesByDate(idCar:String, dateRamassage:String): List<Ramassage>{
        return pointageAPI?.getRamassagesByDateAndCar(idCar,dateRamassage)?: listOf()
    }

    suspend fun getLivraisonByDate(idCar:String, dateLivraison:String): List<Ramassage>{
        return pointageAPI?.getLivraisonsByDateAndCar(idCar,dateLivraison)?: listOf()
    }

}