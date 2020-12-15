package mg.pulse.pointagecar.models.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource

class Chauffeur(
    @SerializedName("id") @Expose var id: String,
    @SerializedName("nom") @Expose var nom: String,
    @SerializedName("prenom") @Expose var prenom: String,
    @SerializedName("cin") @Expose var cin: String,
    @SerializedName("telephone") @Expose var telephone: String,
    @SerializedName("deleted") @Expose var isDeleted: Boolean
) : ResponseBody() {

    override fun contentLength(): Long = contentLength()
    override fun contentType(): MediaType? = contentType()
    override fun source(): BufferedSource {
        TODO("Not yet implemented")
    }
}