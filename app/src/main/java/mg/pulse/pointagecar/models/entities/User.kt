package mg.pulse.pointagecar.models.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource

data class User(
    @SerializedName("id") @Expose var id: String,
    @SerializedName("matricule") @Expose var matricule: String,
    @SerializedName("lastname") @Expose var lastName: String,
    @SerializedName("firstname") @Expose var firstName: String,
    @SerializedName("phonenumber") @Expose var phoneNumber: String
) : ResponseBody() {

    val fullName: String
    get(){ return lastName+" "+firstName}

    override fun contentLength(): Long = contentLength()
    override fun contentType(): MediaType? = contentType()
    override fun source(): BufferedSource {
        TODO("Not yet implemented")
    }
}