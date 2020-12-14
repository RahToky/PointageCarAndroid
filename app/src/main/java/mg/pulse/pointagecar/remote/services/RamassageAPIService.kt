package mg.pulse.pointagecar.remote.services

import android.util.Log
import mg.pulse.pointagecar.models.entities.Collaborateur
import mg.pulse.pointagecar.models.entities.Ramassage
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class RamassageAPIService : BaseAPIService() {

    suspend fun getRamassagesByDate(idCar:String, dateRamassage:String): List<Ramassage>{
        return pointageAPI?.getRamassagesByDateAndCar(idCar,dateRamassage)?: listOf()
    }

    suspend fun getCurrentRamassages(idCar:String): List<Ramassage>{
        var simpleDateFormat:DateFormat = SimpleDateFormat("yyyy-MM-dd")
        return getRamassagesByDate(idCar,simpleDateFormat.format(Date()))
    }
}