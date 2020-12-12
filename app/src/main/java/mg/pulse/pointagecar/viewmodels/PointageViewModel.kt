package mg.pulse.pointagecar.viewmodels

import android.R
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mg.pulse.pointagecar.models.services.CollaboService
import mg.pulse.pointagecar.models.entities.Collaborateur
import mg.pulse.pointagecar.remote.services.CollaboAPIRepository

class PointageViewModel: ViewModel() {

    private var collaboList:MutableLiveData<List<Collaborateur>> = MutableLiveData<List<Collaborateur>>()
    private val parentJob = SupervisorJob()
    private val coroutineScope = CoroutineScope(parentJob + Dispatchers.Main)

    private val collaboAPIRepository: CollaboAPIRepository = CollaboAPIRepository()

    fun initAPI(context: Context){
        val errorHandler = CoroutineExceptionHandler { _, exception ->{}}
        coroutineScope.launch(errorHandler) {
            collaboList.value = collaboAPIRepository.getCollaborateurs()
        }
    }

    fun getAllCollabo() = collaboList

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}