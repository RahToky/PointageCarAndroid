package mg.pulse.pointagecar.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mg.pulse.pointagecar.models.entities.Ramassage
import mg.pulse.pointagecar.remote.services.PointageAPIService
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PointageViewModel: ViewModel() {

    private val parentJob = SupervisorJob()
    private val coroutineScope = CoroutineScope(parentJob + Dispatchers.Main)
    private val pointageAPIRepository: PointageAPIService = PointageAPIService()
    private val simpleDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

    private var datePointage:String = simpleDateFormat.format(Date())
    private var ramassageList: MutableLiveData<List<Ramassage>> = MutableLiveData<List<Ramassage>>()
    private var livraisonList: MutableLiveData<List<Ramassage>> = MutableLiveData<List<Ramassage>>()
    var connectionTentativeCount:Int = 3

    fun initAPI(idCar:String, dateRamassage: String?){
        if(dateRamassage != null)
            this.datePointage = dateRamassage
        this.datePointage = dateRamassage!!
        initRamassageList(idCar,dateRamassage)
        initLivraisonList(idCar,dateRamassage)
    }

    fun initRamassageList(idCar:String, dateRamassage: String?){
        if(dateRamassage != null)
            this.datePointage = dateRamassage
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Log.i("MyTag","Exception === ${exception.message}")
            if(exception.message?.compareTo("timeout",true) == 0){
                if(connectionTentativeCount>0) {
                    initLivraisonList(idCar, this.datePointage)
                    connectionTentativeCount --
                }
            }
        }
        coroutineScope.launch(errorHandler){
            ramassageList.value = pointageAPIRepository.getRamassagesByDate(idCar,datePointage)
        }
    }

    fun initLivraisonList(idCar:String, dateLivraison: String?){
        if(dateLivraison != null)
            this.datePointage = dateLivraison
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Log.i("MyTag","Exception === ${exception.message}")
            if(exception.message?.compareTo("timeout",true) == 0){
                if(connectionTentativeCount>0) {
                    initLivraisonList(idCar, dateLivraison)
                    connectionTentativeCount --
                }
            }
        }
        coroutineScope.launch(errorHandler){
            livraisonList.value = pointageAPIRepository.getLivraisonByDate(idCar,datePointage)
        }
    }

    fun getRamassages() = ramassageList
    fun getLivraisons() = livraisonList

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}