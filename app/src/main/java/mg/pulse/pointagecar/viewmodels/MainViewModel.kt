package mg.pulse.pointagecar.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import mg.pulse.pointagecar.models.entities.User
import mg.pulse.pointagecar.remote.services.UserAPIService

class MainViewModel: BaseViewModel() {

    private val userAPIService: UserAPIService = UserAPIService()

    var driver:LiveData<User> = MutableLiveData()
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
}