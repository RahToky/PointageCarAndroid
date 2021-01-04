package mg.pulse.pointagecar.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource

class AuthResponse(
    @SerializedName("status") @Expose var status:String,
    @SerializedName("data") @Expose var data:AuthDataResponse,
    @SerializedName("message") @Expose var message:String
):ResponseBody() {
    override fun contentLength(): Long = contentLength()
    override fun contentType(): MediaType? = contentType()
    override fun source(): BufferedSource {
        TODO("Not yet implemented")
    }
}