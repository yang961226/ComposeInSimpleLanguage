package com.sundayting.composeinsimplelanguage.one

import androidx.lifecycle.ViewModel

class OneViewModel: ViewModel() {

    data class UiState(
        val name:String,
    )

}