package mg.pulse.pointagecar.remote.services

import mg.pulse.pointagecar.models.entities.Car
import mg.pulse.pointagecar.models.entities.Pointing
import mg.pulse.pointagecar.remote.PointageAPI

class PointingAPIService : BaseAPIService() {

    suspend fun findPickupPointingByDateAndCar(idCar:String, dateRamassage:String): List<Pointing>{
        return PointageAPI.getInstance(token)?.findPickupPointingByDateAndCar(idCar,dateRamassage)?: listOf()
    }

    suspend fun savePickupPointing(pointing: Pointing){
        PointageAPI.getInstance(token)?.savePickupPointing(pointing)
    }

    suspend fun findDeliveryPointingByDateAndCar(idCar:String, dateLivraison:String): List<Pointing>{
        return PointageAPI.getInstance(token)?.findDeliveryPointingByDateAndCar(idCar,dateLivraison)?: listOf()
    }

    suspend fun saveDeliveryPointing(pointing: Pointing){
        PointageAPI.getInstance(token)?.saveDeliveryPointing(pointing)
    }

    suspend fun findCarById(id: String): Car? {
        return PointageAPI.getInstance(token)?.findCarById(id)
    }
}