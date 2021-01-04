package mg.pulse.pointagecar.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import mg.pulse.pointagecar.remote.models.AuthRequest
import mg.pulse.pointagecar.remote.models.AuthResponse
import mg.pulse.pointagecar.remote.services.UserAPIService

class AuthViewModel:BaseViewModel() {

    private val userAPIService: UserAPIService = UserAPIService()
    private var auth:AuthRequest? = null
    var authResponse: MutableLiveData<AuthResponse> = MutableLiveData()

    fun authentificate(login:String, pass:String){
        auth = AuthRequest(login,pass)
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Log.i("MyTag","initCollaboraterByMatricule Exception === ${exception.message}")
            errorMessage.value = exception.message
        }
        coroutineScope.launch(errorHandler){
            authResponse.value = userAPIService.authentificate(auth!!)
        }
    }
}