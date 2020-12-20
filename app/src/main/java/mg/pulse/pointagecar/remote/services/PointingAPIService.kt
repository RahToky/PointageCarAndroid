package mg.pulse.pointagecar.remote.services

import mg.pulse.pointagecar.models.entities.Pointing

class PointingAPIService : BaseAPIService() {

    suspend fun findRamassagesByDateAndCar(idCar:String, dateRamassage:String): List<Pointing>{
        return pointageAPI?.findRamassagesByDateAndCar(idCar,dateRamassage)?: listOf()
    }

    suspend fun findLivraisonsByDateAndCar(idCar:String, dateLivraison:String): List<Pointing>{
        return pointageAPI?.findLivraisonsByDateAndCar(idCar,dateLivraison)?: listOf()
    }

}