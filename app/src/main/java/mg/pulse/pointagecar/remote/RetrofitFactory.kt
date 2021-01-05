package mg.pulse.pointagecar.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    private val BASE_URL: String = "https://cryptic-castle-42591.herokuapp.com/"

    companion object {
        private var retrofit: Retrofit? = null
        var token:String? = null
        fun getInstance(token: String?): Retrofit {
            if (retrofit == null) {
                this.token = token?:null
                RetrofitFactory(token)
            }else{
                if(token?.let { this.token?.compareTo(it,false) } != 0){
                    this.token = token
                    RetrofitFactory(token)
                }
            }
            return retrofit!!
        }
    }

    private constructor(token: String?) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val newRequest =
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                chain.proceed(newRequest)
            })
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

}