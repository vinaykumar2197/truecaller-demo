package  com.vinay.truecaller.ui.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vinay.truecaller.db.DatabaseHelper
import com.vinay.truecaller.network.ApiHelper
import com.vinay.truecaller.ui.MainViewModel

class ViewModelFactory(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper?) :
    ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(apiHelper, dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}