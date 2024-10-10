package com.foke.together.presenter.viewmodel

import android.content.Context
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.lifecycle.ViewModel
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCase
import com.foke.together.domain.interactor.entity.CutFrameType
import com.foke.together.util.AppLog
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class GenerateTwoRowImageViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val generatePhotoFrameUseCase: GeneratePhotoFrameUseCase
): ViewModel() {
    val cutFrameType: CutFrameType = generatePhotoFrameUseCase.getCutFrameType()
    val imageUri = generatePhotoFrameUseCase.getCapturedImageListUri()

    suspend fun generateImage(graphicsLayer: GraphicsLayer) {
        val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
        val finalExternalImageUri = generatePhotoFrameUseCase.saveGraphicsLayerImage(bitmap, "final_two_row")
        val finalInternalImageUri = generatePhotoFrameUseCase.saveFinalImage(bitmap, "final_two_row")
        AppLog.d("GenerateImageViewModel", "generateTwoRowImage" ,"twoRow: $finalExternalImageUri")
    }
}