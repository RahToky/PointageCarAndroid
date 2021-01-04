package mg.pulse.pointagecar.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthRequest(@SerializedName("login") @Expose var login:String, @SerializedName("password") @Expose var password:String) {
}