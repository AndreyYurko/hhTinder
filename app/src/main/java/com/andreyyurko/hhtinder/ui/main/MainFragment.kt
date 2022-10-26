package com.andreyyurko.hhtinder.ui.main

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    companion object {
        const val LOG_TAG = "Main Fragment"
    }

    private lateinit var imagesList : MutableList<Drawable?>
    private var index = 0

    private val viewBinding by viewBinding(FragmentMainBinding::bind)

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*imagesList = context?.let { mutableListOf(
            ContextCompat.getDrawable(
                it,
                R.drawable.shrek
            ),
            ContextCompat.getDrawable(
                it,
                R.drawable.shrek2
            ),
            ContextCompat.getDrawable(
                it,
                R.drawable.shrek3
            ),
            ContextCompat.getDrawable(
                it,
                R.drawable.spanchbob1
            ),
            ContextCompat.getDrawable(
                it,
                R.drawable.spanchbob2
            ),
        )
        } ?: mutableListOf()

        viewBinding.iconImageShown.setImageDrawable(
            imagesList[index]
        )
        viewBinding.iconImageNext.setImageDrawable(
            imagesList[index+1]
        )
        viewBinding.iconImageNext.alpha = 0f

        val swipeLeftAnimatorSet = setupLeftSwipeAnim()
        val swipeRightAnimatorSet = setupRightSwipeAnim()
        val fadeIn = ObjectAnimator.ofFloat(
            viewBinding.iconImageNext,
            "alpha",
            0f,
            1f
        )
        fadeIn.duration = 500

        viewBinding.logoRelativeLayout.setOnTouchListener(object : GesturesHandler(context, viewBinding.logoRelativeLayout) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                swipeLeftAnimatorSet.start()
                fadeIn.start()
            }
            override fun onSwipeRight() {
                super.onSwipeRight()
                swipeRightAnimatorSet.start()
                fadeIn.start()
            }
            override fun onSwipeUp() {
                super.onSwipeUp()
            }
            override fun onSwipeDown() {
                super.onSwipeDown()
            }

        })*/
    }
/*
    private fun setupLeftSwipeAnim(): AnimatorSet {
        val leftPivotY = ObjectAnimator.ofFloat(
            viewBinding.iconImageShown,
            "pivotY",
            viewBinding.iconImageShown.translationY + resources.getDimensionPixelSize(R.dimen.main_fragment_logo_size)
        )
        val leftPivotX = ObjectAnimator.ofFloat(
            viewBinding.iconImageShown,
            "pivotX",
            viewBinding.iconImageShown.translationX
        )
        return setupRightAndLeftAnim(-1, leftPivotX, leftPivotY)
    }

    private fun setupRightSwipeAnim(): AnimatorSet {
        val rightPivotY = ObjectAnimator.ofFloat(
            viewBinding.iconImageShown,
            "pivotY",
            viewBinding.iconImageShown.translationY + resources.getDimensionPixelSize(R.dimen.main_fragment_logo_size)
        )
        val rightPivotX = ObjectAnimator.ofFloat(
            viewBinding.iconImageShown,
            "pivotX",
            viewBinding.iconImageShown.translationX + resources.getDimensionPixelSize(R.dimen.main_fragment_logo_size)
        )
        return setupRightAndLeftAnim(1, rightPivotX, rightPivotY)
    }

    private fun setupRightAndLeftAnim(sign: Int, pivotX: ObjectAnimator, pivotY: ObjectAnimator): AnimatorSet {
        val rotation = ObjectAnimator.ofFloat(
            viewBinding.iconImageShown,
            "rotation",
            0f,
            45f * sign
        )
        val transition = ObjectAnimator.ofFloat(
            viewBinding.iconImageShown,
            "translationX",
            viewBinding.iconImageShown.translationX,
            1000f * sign
        )
        transition.duration = 300
        transition.startDelay = 200
        val fadeOut = ObjectAnimator.ofFloat(
            viewBinding.iconImageShown,
            "alpha",
            1f,
            0f
        )
        fadeOut.duration = 500

        val swipeAnimatorSet = AnimatorSet()
        swipeAnimatorSet.playTogether(pivotX, pivotY, rotation, transition, fadeOut)
        swipeAnimatorSet.duration = 300

        swipeAnimatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                index += 1
                Log.d(MainFragment.LOG_TAG, "index = $index")
                if (index + 1 < (imagesList.size)) {
                    viewBinding.iconImageShown.translationX = viewBinding.iconImageNext.translationX
                    viewBinding.iconImageShown.translationY = viewBinding.iconImageNext.translationY
                    viewBinding.iconImageShown.rotation = viewBinding.iconImageNext.rotation
                    viewBinding.iconImageShown.setImageDrawable(
                        imagesList[index]
                    )
                    viewBinding.iconImageShown.alpha = 1f
                    viewBinding.iconImageNext.alpha = 0f
                    viewBinding.iconImageNext.setImageDrawable(
                        imagesList[index+1]
                    )
                    imagesList.add(
                        context?.let {
                            ContextCompat.getDrawable(
                                it,
                                R.drawable.shrek
                            )
                        },
                    )
                }
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        return swipeAnimatorSet
    }*/
}