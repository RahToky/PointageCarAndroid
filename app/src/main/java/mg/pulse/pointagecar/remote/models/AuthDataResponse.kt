package mg.pulse.pointagecar.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthDataResponse(
    @SerializedName("login") @Expose var login:String,
    @SerializedName("token") @Expose var token:String,
    @SerializedName("iduser") @Expose var idUser:String,
    @SerializedName("matricule") @Expose var matricule:String,
    @SerializedName("firstname") @Expose var firstName:String,
    @SerializedName("lastname") @Expose var lastName:String)
{}