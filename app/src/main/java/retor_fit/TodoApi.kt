package retor_fit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TodoApi {

    @GET("/todos")
    //fun getTodos(@Query("KEY") key :String): Response<List<todo>> when you want to pass api key to the api
    suspend fun getTodos(): Response<List<Todo>>



    //@POST("/createTodo") if you want to post json to api
    // fun createTodo(): Response<createTodo>
}