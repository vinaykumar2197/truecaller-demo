package com.vinay.truecaller.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.vinay.truecaller.R
import com.vinay.truecaller.network.ApiHelperInterface
import com.vinay.truecaller.network.RetrofitBuilder
import com.vinay.truecaller.ui.util.ViewModelFactory
import com.vinay.truecaller.util.Util
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        btMakeRequest.setOnClickListener {

            if(Util.isInternetConnected(this)){
//                showProgress()
                getTruecallerPage()
            }
            else{
                Toast.makeText(this,"Please check your internet connection",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperInterface(RetrofitBuilder.apiInterface),
                null
            )
        ).get(MainViewModel::class.java)
    }


    private fun getTruecallerPage(){

        viewModel.fetchTruecaller10thCharacterRequest()
//        viewModel.fetchTruecallerEvery10thCharacterRequest()
//        viewModel.fetchTruecallerWordCounterRequest()

        viewModel.getTruecaller10thCharLiveData().observe(this, {
//            hideProgress()
            var isWhiteSpace:  Boolean = if (it != null) it.isWhitespace() else false

            tvResult.setText("10th character Result :   ${it}  whitespace value : ${isWhiteSpace}")
            Log.e(TAG, " 10th character Result :   ${it}" )

        })

        viewModel.getTruecallerEvery10thCharLiveData().observe(this, {

            tvResult.setText("Every 10th character Array :   ${it}")
            Log.e(TAG, " Every 10th character Array :   ${it.toString()}" )

        })


        viewModel.getTruecallerWordCounterLiveData().observe(this, {

            tvResult.setText("Word Count Result :   ${it}")

            Log.e(TAG, "Word Count Result :   ${it}" )


        })
    }

    fun showProgress(){
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgress(){
        progressBar.visibility = View.GONE
    }

}