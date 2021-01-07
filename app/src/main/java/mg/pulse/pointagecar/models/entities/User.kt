package mg.pulse.pointagecar.models.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource

data class User(
    @SerializedName("id") @Expose var id: String?=null,
    @SerializedName("matricule") @Expose var matricule: String?=null,
    @SerializedName("lastname") @Expose var lastName: String?=null,
    @SerializedName("firstname") @Expose var firstName: String?=null,
    @SerializedName("phonenumber") @Expose var phoneNumber: String?=null,
    @SerializedName("email") @Expose var email: String?=null
) : ResponseBody() {

    val fullName: String
    get(){ return lastName+" "+firstName}

    override fun contentLength(): Long = contentLength()
    override fun contentType(): MediaType? = contentType()
    override fun source(): BufferedSource {
        TODO("Not yet implemented")
    }
}