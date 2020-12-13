package mg.pulse.pointagecar.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mg.pulse.pointagecar.models.entities.Collaborateur
import mg.pulse.pointagecar.models.entities.Ramassage
import mg.pulse.pointagecar.remote.services.CollaboAPIService
import mg.pulse.pointagecar.remote.services.RamassageAPIService

class PointageViewModel: ViewModel() {

    private val parentJob = SupervisorJob()
    private val coroutineScope = CoroutineScope(parentJob + Dispatchers.Main)
    private val collaboAPIRepository: CollaboAPIService = CollaboAPIService()
    private val ramassageAPIRepository: RamassageAPIService = RamassageAPIService()

    private var collaboList: MutableLiveData<List<Collaborateur>> = MutableLiveData<List<Collaborateur>>()
    private var ramassageList: MutableLiveData<List<Ramassage>> = MutableLiveData<List<Ramassage>>()

    fun initAPI(idCar:String){
        val errorHandler = CoroutineExceptionHandler { _, exception ->{}}
        coroutineScope.launch(errorHandler){
            ramassageList.value = ramassageAPIRepository.getRamassagesByIdCar(idCar)
        }
    }

    fun getCurrentRamassage() = ramassageList

    fun getCollaboInRamassage(ramassageList:List<Ramassage> = listOf()):MutableList<Collaborateur>{
        var res:MutableList<Collaborateur> = mutableListOf()
        for(ramassage in ramassageList){
            res.add(ramassage.collaborateur)
        }
        return res
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}