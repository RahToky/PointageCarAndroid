package mg.pulse.pointagecar.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthDataResponse(@SerializedName("login") @Expose var login:String, @SerializedName("token") @Expose var token:String) {
}