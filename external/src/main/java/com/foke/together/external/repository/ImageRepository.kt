package com.foke.together.external.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import com.foke.together.domain.interactor.entity.CutFrameType
import com.foke.together.domain.output.ImageRepositoryInterface
import com.foke.together.util.ImageFileUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageRepository @Inject constructor(
    @ApplicationContext private val context: Context
): ImageRepositoryInterface{
    private val cutFrameTypeFlow: MutableStateFlow<CutFrameType> = MutableStateFlow(CutFrameType.MAKER_FAIRE)

    override fun getCutFrameType(type: Int): Flow<CutFrameType> = cutFrameTypeFlow
    override suspend fun setCutFrameType(type: Int) {
        cutFrameTypeFlow.emit(CutFrameType.findBy(type))
    }

    override suspend fun savePkgInternal(image: Bitmap, fileName: String): Uri {
        return ImageFileUtil.saveBitmapInternal(context, image, fileName)
    }

    override fun getPkgInternalUriList(): List<Uri> {
        return context.filesDir.listFiles()?.map {
            Uri.fromFile(it)
        } ?: emptyList()
    }

    override suspend fun clearPkgInternal() {
        context.filesDir.listFiles()?.forEach {
            it.delete()
        }
    }

    override suspend fun saveExternal(image: Bitmap, fileName: String): Uri {
        return ImageFileUtil.saveBitmapMedia(context, image, fileName)
    }

    override fun getUriToBitmap(imageUri: Uri): Bitmap {
        return ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, imageUri))
    }

}