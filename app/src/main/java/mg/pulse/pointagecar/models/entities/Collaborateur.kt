package mg.pulse.pointagecar.models.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import okio.Okio
import okio.buffer

data class Collaborateur(
    @SerializedName("id") @Expose val id: String,
    @SerializedName("matricule") @Expose var matricule: String,
    @SerializedName("nom") @Expose var nom: String,
    @SerializedName("prenom") @Expose var prenom: String,
    @SerializedName("telephone") @Expose var telephone: String
) : ResponseBody() {

    override fun contentLength(): Long = contentLength()
    override fun contentType(): MediaType? = contentType()
    override fun source(): BufferedSource {
        TODO("Not yet implemented")
    }
}