package mg.pulse.pointagecar.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import mg.pulse.pointagecar.models.entities.Car
import mg.pulse.pointagecar.models.entities.Pointing
import mg.pulse.pointagecar.models.entities.User
import mg.pulse.pointagecar.remote.services.PointingAPIService
import mg.pulse.pointagecar.remote.services.UserAPIService

class MainViewModel: BaseViewModel() {

    private val userAPIService: UserAPIService = UserAPIService()
    private val pointingAPIService: PointingAPIService = PointingAPIService()
    var pointingSuccess:MutableLiveData<Boolean> = MutableLiveData(false)
    var collaborater:MutableLiveData<User> = MutableLiveData()

    fun initCollaboraterByMatricule(matricule:String){
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Log.i("MyTag","initCollaboraterByMatricule Exception === ${exception.message}")
            errorMessage.value = exception.message
        }
        coroutineScope.launch(errorHandler){
            collaborater.value = userAPIService.findUserByMatricule(matricule)
        }
    }

    fun savePickupPointing(collaborateur:User, car: Car){
        pointingSuccess.value = false
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Log.i("MyTag","savePickupPointing Exception === ${exception.message}")
            errorMessage.value = exception.message
        }
        coroutineScope.launch(errorHandler){
            pointingAPIService.savePickupPointing(Pointing(null,collaborateur,car,null,null))
            pointingSuccess.value = true
        }
    }

    fun saveDeliveryPointing(collaborateur:User, car: Car){
        pointingSuccess.value = false
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Log.i("MyTag","savePickupPointing Exception === ${exception.message}")
            errorMessage.value = exception.message
        }
        coroutineScope.launch(errorHandler){
            pointingAPIService.saveDeliveryPointing(Pointing(null,collaborateur,car,null,null))
            pointingSuccess.value = true
        }
    }

}