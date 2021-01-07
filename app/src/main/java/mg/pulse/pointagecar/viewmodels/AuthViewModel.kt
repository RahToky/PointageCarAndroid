package mg.pulse.pointagecar.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import mg.pulse.pointagecar.R
import mg.pulse.pointagecar.models.entities.Car
import mg.pulse.pointagecar.remote.models.AuthRequest
import mg.pulse.pointagecar.remote.models.AuthResponse
import mg.pulse.pointagecar.remote.services.PointingAPIService
import mg.pulse.pointagecar.remote.services.UserAPIService
import java.lang.ref.WeakReference

class AuthViewModel(context: Context):BaseViewModel() {

    private val weakContext:WeakReference<Context> = WeakReference(context)
    private val userAPIService: UserAPIService = UserAPIService()
    private val pointingAPIService: PointingAPIService = PointingAPIService()
    private var auth:AuthRequest? = null
    var authResponse: MutableLiveData<AuthResponse> = MutableLiveData()
    var car:MutableLiveData<Car> = MutableLiveData()

    fun authentificate(login:String, pass:String){
        auth = AuthRequest(login,pass)
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            if(exception.message?.contains("",true) == true){
                errorMessage.value = weakContext.get()?.resources?.getString(R.string.server_error)
            }else {
                errorMessage.value = exception.message
            }
        }
        coroutineScope.launch(errorHandler){
            authResponse.value = userAPIService.authentificate(auth!!)
        }
    }

    fun findCarById(id:String){
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            if(exception.message?.contains("",true) == true){
                errorMessage.value = weakContext.get()?.resources?.getString(R.string.server_error)
            }else {
                errorMessage.value = exception.message
            }
        }
        coroutineScope.launch(errorHandler){
            car.value = pointingAPIService.findCarById(id)
        }
    }
}