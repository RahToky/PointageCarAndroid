package mg.pulse.pointagecar.models.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource

open class Pointing(
    @SerializedName("id") @Expose val id: String?,
    @SerializedName("user") @Expose var collaborater: User?,
    @SerializedName("car") @Expose var car: Car?,
    @SerializedName(value="pickupdate", alternate= arrayOf("deliverydate")) @Expose var pointingDate: String?,
    @SerializedName(value="pickuphour", alternate= arrayOf("deliveryhour")) @Expose var pointingHour: String?
) : ResponseBody() {

    override fun contentLength(): Long = contentLength()
    override fun contentType(): MediaType? = contentType()
    override fun source(): BufferedSource {
        TODO("Not yet implemented")
    }
}