package com.egeperk.kotlincrypto.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egeperk.kotlincrypto.R
import com.egeperk.kotlincrypto.adapter.CryptoAdapter
import com.egeperk.kotlincrypto.model.CryptoModel
import com.egeperk.kotlincrypto.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity(), CryptoAdapter.Listener {


    private val BASE_URL = "https://api.nomics.com/v1/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private lateinit var recyclerViewAdapter: CryptoAdapter
    private var job : Job? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //dd8f2f6813f2d37f2cf6fe5e318e7ee170da985c
        //https://api.nomics.com/v1/prices?key=dd8f2f6813f2d37f2cf6fe5e318e7ee170da985c



        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        loadData()

    }

    private fun loadData() {

        //retrofit oluşturma
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)


        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getData()

            withContext(Dispatchers.Main){
            if (response.isSuccessful) {
                response.body()?.let {
                    cryptoModels = ArrayList(it)
                    cryptoModels?.let {
                        recyclerViewAdapter = CryptoAdapter(it,this@MainActivity)
                        recyclerView.adapter = recyclerViewAdapter
                    }
                }

            }
            }
        }



        /*

        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<List<CryptoModel>> {
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {

                if (response.isSuccessful) {
                    response.body()?.let {
                        cryptoModels = ArrayList(it)

                        cryptoModels?.let {
                            recyclerViewAdapter = CryptoAdapter(it,this@MainActivity)
                            recyclerView.adapter = recyclerViewAdapter
                        }


                    }
                }



            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })

*/

    }








        override fun onItemClick(cryptoModel: CryptoModel) {
            Toast.makeText(applicationContext,"Clicked: ${cryptoModel.currency}", Toast.LENGTH_SHORT).show()
        }

        override fun onDestroy() {
            super.onDestroy()
            job?.cancel()

        }



}