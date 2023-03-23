package retor_fit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api:TodoApi by lazy{
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create()) // json from website converts it dataclass of Todo
            .build()
            .create(TodoApi::class.java)
    }
}