package com.vinay.truecaller.ui

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.vinay.truecaller.network.ApiHelper
import com.vinay.truecaller.db.DatabaseHelper
import com.vinay.truecaller.util.Util
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashSet

class MainViewModel(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper?)
    : ViewModel()
{

    private val truecaller10thCharLiveData = MutableLiveData<Char>()
    private val truecallerEvery10thCharLiveData = MutableLiveData<List<Char>>()
    private val truecallerWordCounterLiveData = MutableLiveData<StringBuilder>()
    private val progressBarLivedata = MutableLiveData<Boolean>()


    fun fetchTruecaller10thCharacterRequest() {


        viewModelScope.launch {
            try {
                coroutineScope {
                    var apiHit =  async { apiHelper.getTruecallerPage()!!}.await()
//                val pageData = apiHit.map {
//                    it
//                }

                    var pageData = apiHit.get(9)

                    truecaller10thCharLiveData.postValue(pageData)

                    fetchTruecallerEvery10thCharacterRequest()

                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "fetchHtmlPage: ${e.printStackTrace()}" )
            }
        }
    }

    fun fetchTruecallerEvery10thCharacterRequest() {
        viewModelScope.launch {

            try {

                coroutineScope {

                    var apiHit =  async {apiHelper.getTruecallerPage()!!}.await()

                    val pageData = apiHit.map {
                        it
                    }
//                    Log.e("data______",pageData.toString() )


                    var listChars = mutableListOf<Char>()
                    for (i in 1..pageData.size) {
//                        Log.e("data______",""+i  )
                        if (i % 10 == 0) {
                            listChars.add(pageData[i-1])
                        }
                    }

                    truecallerEvery10thCharLiveData.postValue(listChars)

                    fetchTruecallerWordCounterRequest()
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "fetchHtmlPage: ${e.printStackTrace()}" )
            }
        }
    }


    fun fetchTruecallerWordCounterRequest() {
        viewModelScope.launch {

            try {
                var apiHit = async { apiHelper.getTruecallerPage()!!}.await()
//            var delimiter1 = "\\s+";
//            var delimiter1 = Pattern.compile("\\s+");
                var sb = StringBuilder()
//            val parts = apiHit.split(delimiter1, ignoreCase = true)
                val parts = apiHit.split(Pattern.compile("\\s+"))
                val uniqueWords: Set<String> = HashSet<String>(parts)


                for( words in uniqueWords){
                    sb.append(words + ": " +  Collections.frequency(parts, words)+"\n")
                    Log.e("----->",words + ": " +  Collections.frequency(parts, words))
                }

                truecallerWordCounterLiveData.postValue(sb)
            } catch (e: Exception) {
                Log.e("MainViewModel", "fetchHtmlPage: ${e.printStackTrace()}" )
            }
        }
    }



    fun getTruecaller10thCharLiveData(): LiveData<Char> {
        return truecaller10thCharLiveData
    }



    fun getTruecallerEvery10thCharLiveData(): LiveData<List<Char>> {
        return truecallerEvery10thCharLiveData
    }


    fun getTruecallerWordCounterLiveData(): LiveData<StringBuilder> {
        return truecallerWordCounterLiveData
    }


}