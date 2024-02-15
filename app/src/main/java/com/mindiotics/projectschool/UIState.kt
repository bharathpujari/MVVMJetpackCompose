package com.mindiotics.projectschool

import com.mindiotics.projectschool.ui.data.School

sealed class UIState <out T> {
    data class Success<T> (val data: T): UIState<T>()
    data class Error(val message:String): UIState<Nothing>()
    object Loading : UIState<Nothing>()
}