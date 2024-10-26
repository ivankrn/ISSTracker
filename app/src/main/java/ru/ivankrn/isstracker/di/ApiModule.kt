package ru.ivankrn.isstracker.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import ru.ivankrn.isstracker.data.api.SatellitePassesApi
import ru.ivankrn.isstracker.util.AppConstants.BASE_URL
import ru.ivankrn.isstracker.util.AppConstants.REQUEST_TIMEOUT_IN_SECONDS

fun provideGson(): Gson {
    return GsonBuilder()
        .registerTypeAdapter(ZonedDateTime::class.java, JsonDeserializer<ZonedDateTime> { json, typeOfT, context ->
            val dateTimeFormatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSXXX")
                .withZone(ZoneOffset.UTC)
            if (json.asJsonPrimitive.isString) {
                ZonedDateTime.parse(json.asString, dateTimeFormatter)
            } else {
                null
            }
        })
        .setLenient()
        .create()
}


fun provideHttpClient(context: Context): OkHttpClient {
    return OkHttpClient.Builder()
        .apply {
            readTimeout(REQUEST_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        }
        .addInterceptor(ChuckerInterceptor(context))
        .build()
}

fun provideRetrofit(gson: Gson, httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

fun provideApi(retrofit: Retrofit): SatellitePassesApi = retrofit.create(SatellitePassesApi::class.java)

val apiModule = module {
    single { provideGson() }
    single { provideHttpClient(androidContext()) }
    single { provideRetrofit(get(), get()) }
    single { provideApi(get()) }
}