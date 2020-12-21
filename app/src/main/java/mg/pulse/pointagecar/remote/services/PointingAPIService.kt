package mg.pulse.pointagecar.remote.services

import mg.pulse.pointagecar.models.entities.Pointing

class PointingAPIService : BaseAPIService() {

    suspend fun findRamassagesByDateAndCar(idCar:String, dateRamassage:String): List<Pointing>{
        return pointingAPI?.findRamassagesByDateAndCar(idCar,dateRamassage)?: listOf()
    }

    suspend fun savePickupPointing(pointing: Pointing){
        pointingAPI?.savePickupPointing(pointing)
    }

    suspend fun findLivraisonsByDateAndCar(idCar:String, dateLivraison:String): List<Pointing>{
        return pointingAPI?.findLivraisonsByDateAndCar(idCar,dateLivraison)?: listOf()
    }

    suspend fun saveDeliveryPointing(pointing: Pointing){
        pointingAPI?.saveDeliveryPointing(pointing)
    }
}