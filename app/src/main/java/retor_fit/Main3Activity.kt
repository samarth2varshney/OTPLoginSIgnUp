package retor_fit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drive.databinding.ActivityMain3Binding
import retrofit2.HttpException
import java.io.IOException

const val TAG = "Main3Activity"
class Main3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try{
                RetrofitInstance.api.getTodos()
            } catch (e: IOException){
                // exception of no network connection
                Log.e(TAG,"no internet")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException){
                Log.e(TAG,"unexpected response")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body() !=null){
                todoAdapter.todos = response.body()!!
            }else{
                Log.e(TAG,"response not succesfull")
            }
            binding.progressBar.isVisible = false
        }
    }
    private fun setupRecyclerView() = binding.rvTodos.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@Main3Activity)
    }
}