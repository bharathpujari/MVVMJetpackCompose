package com.mindiotics.projectschool.ui.viewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindiotics.projectschool.UIState
import com.mindiotics.projectschool.ui.data.RetrofitClient
import com.mindiotics.projectschool.ui.data.School
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HighSchoolViewModel : ViewModel() {

    private val apiService = RetrofitClient.apiService

    var schoolResult = MutableStateFlow<UIState<List<School>>>(UIState.Loading)

     @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
     fun fetchHighSchools(){
         var highSchools: List<School> = emptyList()
         viewModelScope.launch(Dispatchers.IO) {
            try {
                highSchools = apiService.getHighSchools()
                schoolResult.value = UIState.Success(highSchools)
            } catch(e: Exception) {
                val errorMessage = when(e) {
                    is HttpException -> "Http Error ${e.printStackTrace()}"
                    else ->
                        "Network Error ${e.message}"
                }

                schoolResult.value = UIState.Error(errorMessage)
            }
        }
    }
}

