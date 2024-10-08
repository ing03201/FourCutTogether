package com.foke.together.domain.interactor

import com.foke.together.domain.interactor.entity.CutFrameType
import com.foke.together.domain.output.AppPreferenceInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCutFrameTypeUseCase @Inject constructor(
    private val appPreference: AppPreferenceInterface
) {
    operator fun invoke(): Flow<Int> = appPreference.getCutFrameType().map { it.ordinal }
}