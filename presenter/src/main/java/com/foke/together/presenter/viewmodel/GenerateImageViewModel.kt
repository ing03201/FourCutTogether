package com.foke.together.presenter.viewmodel

import android.content.Context
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCase
import com.foke.together.domain.interactor.entity.CutFrameType
import com.foke.together.util.AppLog
import com.foke.together.util.ImageFileUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerateImageViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val generatePhotoFrameUseCase: GeneratePhotoFrameUseCase
): ViewModel() {
    val cutFrameType: CutFrameType = generatePhotoFrameUseCase.getCutFrameType()
    val imageUri = generatePhotoFrameUseCase.getCapturedImageListUri()

    private fun generateImageJob(graphicsLayer: GraphicsLayer)= viewModelScope.launch {
        val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
        val finalImageUri = ImageFileUtil.saveBitmapInternal(context, bitmap, "finalImage")
        AppLog.d("GenerateImageViewModel", "generateImageJob: finalImageUri: $finalImageUri")
    }

    fun generateImage(graphicsLayer: GraphicsLayer): Flow<Boolean> = flow {
        emit(generateImageJob(graphicsLayer).isCompleted)
    }
}