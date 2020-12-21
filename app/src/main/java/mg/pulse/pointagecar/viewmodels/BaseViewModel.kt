package mg.pulse.pointagecar.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseViewModel:ViewModel() {
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    protected val parentJob = SupervisorJob()
    protected val coroutineScope = CoroutineScope(parentJob + Dispatchers.Main)
}