package com.foke.together.domain.interactor

import com.foke.together.domain.interactor.entity.CutFrameType
import com.foke.together.domain.output.AppPreferenceInterface
import javax.inject.Inject

class SetCutFrameTypeUseCase @Inject constructor(
    private val appPreference: AppPreferenceInterface
) {
    suspend operator fun invoke(cutFrameType: CutFrameType) =
        appPreference.setCutFrameType(cutFrameType)
}