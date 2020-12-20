package mg.pulse.pointagecar.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mg.pulse.pointagecar.models.entities.Pointing
import mg.pulse.pointagecar.remote.services.PointingAPIService
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PointingViewModel: ViewModel() {

    private val parentJob = SupervisorJob()
    private val coroutineScope = CoroutineScope(parentJob + Dispatchers.Main)
    private val pointageAPIRepository: PointingAPIService = PointingAPIService()
    private val simpleDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

    private var datePointage:String = simpleDateFormat.format(Date())
    var pickupPointingList: MutableLiveData<List<Pointing>> = MutableLiveData<List<Pointing>>()
    var deliveryPointingList: MutableLiveData<List<Pointing>> = MutableLiveData<List<Pointing>>()
    var errorMessage:MutableLiveData<String> = MutableLiveData()

    @Throws(Exception::class)
    fun initRamassageList(idCar:String, dateRamassage: String?){
        if(dateRamassage != null)
            this.datePointage = dateRamassage
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Log.i("MyTag","initRamassageList Exception === ${exception.message}")
            errorMessage.value = exception.message
        }
        coroutineScope.launch(errorHandler){
            pickupPointingList.value = pointageAPIRepository.findRamassagesByDateAndCar(idCar,datePointage)
        }
    }

    @Throws(Exception::class)
    fun initLivraisonList(idCar:String, dateLivraison: String?){
        if(dateLivraison != null)
            this.datePointage = dateLivraison
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Log.i("MyTag","initLivraisonList Exception === ${exception.message}")
            errorMessage.value = exception.message
        }
        coroutineScope.launch(errorHandler){
            deliveryPointingList.value = pointageAPIRepository.findLivraisonsByDateAndCar(idCar,datePointage)
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}