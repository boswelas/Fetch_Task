package com.example.fetchtask

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchtask.adapters.RvAdapter
import com.example.fetchtask.databinding.ActivityMainBinding
import com.example.fetchtask.models.FetchItemsItem
import com.example.fetchtask.utils.ItemUtils
import com.example.fetchtask.utils.RetrofitInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var rvAdapter: RvAdapter
    private lateinit var itemsList: List<FetchItemsItem>

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemsList = listOf()

        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getAllItems()
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Application error ${e.message}", Toast.LENGTH_LONG).show()
                }
                return@launch
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "HTTP error ${e.message}", Toast.LENGTH_LONG).show()
                }
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    itemsList = response.body()!!
                    val groupedItems = ItemUtils.groupAndFilterItems(itemsList)

                    binding.rvMain.apply {
                        rvAdapter = RvAdapter(groupedItems)
                        adapter = rvAdapter
                        layoutManager = LinearLayoutManager(this@MainActivity)
                    }
                }
            }
        }
    }
}
