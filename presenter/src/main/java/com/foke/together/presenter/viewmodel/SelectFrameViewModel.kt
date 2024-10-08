package com.foke.together.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.GetCutFrameTypeUseCase
import com.foke.together.domain.interactor.SetCutFrameTypeUseCase
import com.foke.together.domain.interactor.entity.CutFrameType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectFrameViewModel @Inject constructor(
    getCutFrameTypeUseCase: GetCutFrameTypeUseCase,
    private val setCutFrameTypeUseCase: SetCutFrameTypeUseCase
): ViewModel() {
    val cutFrameType = getCutFrameTypeUseCase().shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        replay = 1
    )

    fun setCutFrameType(index: Int){
        setCutFrameType(CutFrameType.entries[index])
    }

    private fun setCutFrameType(type: CutFrameType){
        viewModelScope.launch {
            setCutFrameTypeUseCase(type)
        }
    }

}