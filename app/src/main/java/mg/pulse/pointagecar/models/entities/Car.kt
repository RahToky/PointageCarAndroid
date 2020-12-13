package mg.pulse.pointagecar.models.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource

class Car(
    @SerializedName("id") @Expose var id: String,
    @SerializedName("immatriculation") @Expose var immatriculation: String,
    @SerializedName("chauffeur") @Expose var chauffeur: Chauffeur,
    @SerializedName("deleted") @Expose var isDeleted: Boolean
) : ResponseBody() {


    override fun contentLength(): Long = contentLength()
    override fun contentType(): MediaType? = contentType()
    override fun source(): BufferedSource {
        TODO("Not yet implemented")
    }

}