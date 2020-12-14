package mg.pulse.pointagecar.models.entities

import android.text.BoringLayout
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource

data class Ramassage(
    @SerializedName("id") @Expose val id: String,
    @SerializedName("collaborateur") @Expose var collaborateur: Collaborateur,
    @SerializedName("car") @Expose var car: Car,
    @SerializedName("dateramassage") @Expose var dateRamassage: String,
    @SerializedName("heureramassage") @Expose var heureRamassage: String,
    @SerializedName("deleted") @Expose var deleted: Boolean
) : ResponseBody() {

    override fun contentLength(): Long = contentLength()
    override fun contentType(): MediaType? = contentType()
    override fun source(): BufferedSource {
        TODO("Not yet implemented")
    }
}