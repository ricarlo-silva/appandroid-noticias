package br.com.ricarlo.common.util.extensions

import androidx.constraintlayout.motion.widget.MotionLayout

inline fun MotionLayout.doOnTransitionCompleted(
        crossinline action: (p0: MotionLayout?, p1: Int) -> Unit
) = addTransitionListener(onTransitionCompleted = action)

inline fun MotionLayout.addTransitionListener(
        crossinline onTransitionStarted: (
                p0: MotionLayout?, p1: Int, p2: Int
        ) -> Unit = { _, _, _ -> },

        crossinline onTransitionChange: (
                p0: MotionLayout?, p1: Int, p2: Int, p3: Float
        ) -> Unit = { _, _, _, _ -> },

        crossinline onTransitionCompleted: (
                p0: MotionLayout?, p1: Int
        ) -> Unit = { _, _ -> },

        crossinline onTransitionTrigger: (
                p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float
        ) -> Unit = { _, _, _, _ -> }

): MotionLayout.TransitionListener {
    val transitionListener = object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            onTransitionStarted.invoke(p0, p1, p2)
        }

        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            onTransitionChange.invoke(p0, p1, p2, p3)
        }

        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            onTransitionCompleted.invoke(p0, p1)
        }

        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            onTransitionTrigger.invoke(p0, p1, p2, p3)
        }

    }
    addTransitionListener(transitionListener)
    return transitionListener
}
