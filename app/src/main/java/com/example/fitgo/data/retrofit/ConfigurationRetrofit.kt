package com.example.fitgo.data.retrofit

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.Navigator
import com.example.common.ConstantesServer
import com.example.fitgo.data.retrofit.calls.EntrenamientosApi
import com.example.fitgo.data.retrofit.calls.UserApi
import com.example.utils.DataStoreTokens
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.net.CookieManager
import java.net.CookiePolicy
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")


@Module
@InstallIn(SingletonComponent::class)
class ConfigurationRetrofit {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("FitGo", Context.MODE_PRIVATE)
    }
    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): DataStoreTokens =
        DataStoreTokens(context)

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(LocalDateTimeAdapter())
            .build()
    }

    @Singleton
    @Provides
    fun getOkHttpClient(): OkHttpClient {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
            .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
            .addInterceptor(loggingInterceptor)
            .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
            .connectionPool(ConnectionPool(1, 1, TimeUnit.SECONDS))
            .build()
    }

    @Singleton
    @Provides
    @Named("Service1")
    fun retrofits(gson: Gson, clientOK: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl("http://192.168.1.24:8080")

            .addConverterFactory(GsonConverterFactory.create(gson))

            .client(clientOK)
            .build()
    }

    @Singleton
    @Provides
    @Named("Service2")
    fun retrofitsRest(gson: Gson, clientOK: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl("http://192.168.1.24:8081")

            .addConverterFactory(GsonConverterFactory.create(gson))

            .client(clientOK)
            .build()
    }


    @Singleton
    @Provides
    fun getApiEntrenamientos(  @Named("Service2")retrofit: Retrofit): EntrenamientosApi {
        return retrofit.create(EntrenamientosApi::class.java)
    }
    @Singleton
    @Provides
    fun getApiCredentials( @Named("Service1")retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
    @Singleton
    @Provides
    fun getGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(
                LocalDateTime::class.java,
                JsonDeserializer { json: JsonElement, _: Type?, _: JsonDeserializationContext? ->
                    LocalDateTime.parse(
                        json.asJsonPrimitive.asString
                    )
                } as JsonDeserializer<LocalDateTime>)
            .registerTypeAdapter(
                LocalDateTime::class.java,
                JsonSerializer { localDateTime: LocalDateTime, _: Type?, _: JsonSerializationContext? ->
                    JsonPrimitive(
                        localDateTime.toString()
                    )
                } as JsonSerializer<LocalDateTime>)
            .registerTypeAdapter(
                LocalDate::class.java,
                JsonDeserializer { json: JsonElement, _: Type?, _: JsonDeserializationContext? ->
                    LocalDate.parse(
                        json.asJsonPrimitive.asString
                    )
                } as JsonDeserializer<LocalDate>)
            .registerTypeAdapter(
                LocalDate::class.java,
                JsonSerializer { localDate: LocalDate, _: Type?, _: JsonSerializationContext? ->
                    JsonPrimitive(
                        localDate.toString()
                    )
                } as JsonSerializer<LocalDate>
            ).create()
    }
    class LocalDateTimeAdapter {

        @ToJson
        fun toJson(value: LocalDateTime): String {
            return value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }

        @FromJson
        fun fromJson(value: String): LocalDateTime {
            return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }
    }
}