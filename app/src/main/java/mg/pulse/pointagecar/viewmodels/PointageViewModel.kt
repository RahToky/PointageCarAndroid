package mg.pulse.pointagecar.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mg.pulse.pointagecar.models.entities.Ramassage
import mg.pulse.pointagecar.remote.services.RamassageAPIService
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PointageViewModel: ViewModel() {

    private val parentJob = SupervisorJob()
    private val coroutineScope = CoroutineScope(parentJob + Dispatchers.Main)
    private val ramassageAPIRepository: RamassageAPIService = RamassageAPIService()
    private val simpleDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

    private var dateRamassage:String = simpleDateFormat.format(Date())
    private var ramassageList: MutableLiveData<List<Ramassage>> = MutableLiveData<List<Ramassage>>()

    fun initAPI(idCar:String, dateRamassage: String?){
        if(dateRamassage != null)
            this.dateRamassage = dateRamassage
        this.dateRamassage = dateRamassage!!
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Log.i("MyTag","Exception === ${exception.message}")
            if(exception.message?.compareTo("timeout",true) == 0){
                initAPI(idCar, this.dateRamassage)
            }
        }
        coroutineScope.launch(errorHandler){
            ramassageList.value = ramassageAPIRepository.getRamassagesByDate(idCar,dateRamassage)
        }
    }

    fun getRamassages() = ramassageList

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}