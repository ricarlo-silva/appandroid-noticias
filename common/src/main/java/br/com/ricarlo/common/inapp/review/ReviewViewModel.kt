package br.com.ricarlo.common.inapp.review

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ricarlo.common.firebase.remoteconfig.Feature
import br.com.ricarlo.common.firebase.remoteconfig.IFirebaseRemoteConfigManager
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import java.lang.Exception

class ReviewViewModel constructor(
        private val reviewManager: ReviewManager,
        private val remoteConfigManager: IFirebaseRemoteConfigManager
) : ViewModel() {

    private var _reviewInfo: ReviewInfo? = null

    private val _reviewFlow = MutableLiveData<ReviewFlow>()
    val reviewFlow: LiveData<ReviewFlow> = _reviewFlow

    @MainThread
    fun requestReviewFlow() {

        if (isEnableInAppReview) {
            if (_reviewInfo == null) {
                reviewManager
                        .requestReviewFlow()
                        .addOnCompleteListener { request ->
                            Log.e("requestReviewFlow", "$request")
                            _reviewInfo = if (request.isSuccessful) {
                                _reviewFlow.postValue(ReviewFlow.LaunchInApp(request.result))
                                request.result
                            } else {
                                _reviewFlow.postValue(ReviewFlow.Error(request.exception))
                                null
                            }
                        }
            }
        } else {
            _reviewFlow.postValue(ReviewFlow.LaunchOutApp)
        }

    }

    private val isEnableInAppReview
            get() = remoteConfigManager.fetchSync(Feature.IN_APP_REVIEW, Boolean::class.java)

}

sealed class ReviewFlow {
    data class LaunchInApp(val reviewInfo: ReviewInfo) : ReviewFlow()
    object LaunchOutApp : ReviewFlow()
    data class Error(val exception: Exception) : ReviewFlow()
}
