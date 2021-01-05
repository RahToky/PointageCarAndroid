package mg.pulse.pointagecar.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import mg.pulse.pointagecar.models.entities.Pointing
import mg.pulse.pointagecar.remote.services.PointingAPIService
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PointingViewModel: BaseViewModel() {

    private val pointageAPIService: PointingAPIService = PointingAPIService()
    private val simpleDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

    private var datePointage:String = simpleDateFormat.format(Date())
    var pickupPointingList: MutableLiveData<List<Pointing>> = MutableLiveData<List<Pointing>>()
    var deliveryPointingList: MutableLiveData<List<Pointing>> = MutableLiveData<List<Pointing>>()
    val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.i("MyTag","CoroutineExceptionHandler : ${exception.message}")
        errorMessage.value = exception.message
    }

    fun initRamassageList(idCar:String, dateRamassage: String?){
        if(dateRamassage != null)
            this.datePointage = dateRamassage
        coroutineScope.launch(errorHandler){
            pickupPointingList.value = pointageAPIService.findPickupPointingByDateAndCar(idCar,datePointage)
        }
    }

    fun initLivraisonList(idCar:String, dateLivraison: String?){
        if(dateLivraison != null)
            this.datePointage = dateLivraison
        coroutineScope.launch(errorHandler){
            deliveryPointingList.value = pointageAPIService.findDeliveryPointingByDateAndCar(idCar,datePointage)
        }
    }

    fun checkPickupPointing(pointing:Pointing){
        coroutineScope.launch(errorHandler){
            pointageAPIService.savePickupPointing(pointing)
        }
    }

    fun checkDeliveryPointing(pointing:Pointing){
        coroutineScope.launch(errorHandler){
            pointageAPIService.saveDeliveryPointing(pointing)
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}