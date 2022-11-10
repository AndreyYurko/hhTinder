package com.andreyyurko.hhtinder.ui.cv

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentCvBinding
import com.andreyyurko.hhtinder.structures.CV
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CVFragment : Fragment(R.layout.fragment_cv) {
    companion object {
        const val LOG_TAG = "Main Fragment"
    }

    private var baseTranslationX = 0F
    private var baseTranslationY = 0F
    private var baseRotation = 0F

    private val viewBinding by viewBinding(FragmentCvBinding::bind)
    private lateinit var viewModel : CVViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[CVViewModel::class.java]
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseRotation = viewBinding.employeeCard.rotation
        baseTranslationX = viewBinding.employeeCard.translationX
        baseTranslationY = viewBinding.employeeCard.translationY


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCVState().collect { state ->
                    when (state) {
                        is CVViewModel.GetCVState.Ok -> {
                            //Log.d(LOG_TAG, "Loading complete!")
                            val index = viewModel.getIndex()
                            setupShownCard(viewModel.getCV(index))
                            setupNextCard(viewModel.getCV(index + 1))
                            viewBinding.progressBar.isVisible = false
                            viewBinding.employeeCard.isVisible = true
                            viewBinding.employeeCardNext.isVisible = true
                            viewBinding.employeeCard.alpha = 1f
                            viewBinding.employeeCardNext.alpha = 0f
                        }
                        is CVViewModel.GetCVState.Error -> {
                            // TODO: обработка ошибки получения CV
                        }
                        is CVViewModel.GetCVState.Loading -> {
                            //Log.d(LOG_TAG, "Loading!")
                            viewBinding.employeeCard.isVisible = false
                            viewBinding.employeeCardNext.isVisible = false
                            viewBinding.progressBar.isVisible = true
                        }
                    }
                }
            }
        }

        val swipeLeftAnimatorSet = setupLeftSwipeAnim()
        val swipeRightAnimatorSet = setupRightSwipeAnim()
        val fadeIn = ObjectAnimator.ofFloat(
            viewBinding.employeeCardNext,
            "alpha",
            0f,
            1f
        )
        fadeIn.duration = 500

        viewBinding.employeeCard.setOnTouchListener(object :
            GesturesHandler(context, viewBinding.employeeCard) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                swipeLeftAnimatorSet.start()
                if (viewModel.getIndex() + 1 != viewModel.getCVListSize()) {
                    fadeIn.start()
                    Log.d(LOG_TAG, "fadeIn")
                }
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                swipeRightAnimatorSet.start()
                if (viewModel.getIndex() + 1 != viewModel.getCVListSize()) {
                    fadeIn.start()
                }
            }

            override fun onSwipeUp() {
                super.onSwipeUp()
            }

            override fun onSwipeDown() {
                super.onSwipeDown()
            }

        })
    }

    private fun setupLeftSwipeAnim(): AnimatorSet {
        val leftPivotY = ObjectAnimator.ofFloat(
            viewBinding.employeeCard,
            "pivotY",
            viewBinding.employeeCard.translationY + resources.getDimensionPixelSize(R.dimen.main_fragment_card_size)
        )
        val leftPivotX = ObjectAnimator.ofFloat(
            viewBinding.employeeCard,
            "pivotX",
            viewBinding.employeeCard.translationX
        )
        return setupRightAndLeftAnim(-1, leftPivotX, leftPivotY)
    }

    private fun setupRightSwipeAnim(): AnimatorSet {
        val rightPivotY = ObjectAnimator.ofFloat(
            viewBinding.employeeCard,
            "pivotY",
            viewBinding.employeeCard.translationY + resources.getDimensionPixelSize(R.dimen.main_fragment_card_size)
        )
        val rightPivotX = ObjectAnimator.ofFloat(
            viewBinding.employeeCard,
            "pivotX",
            viewBinding.employeeCard.translationX + resources.getDimensionPixelSize(R.dimen.main_fragment_card_size)
        )
        return setupRightAndLeftAnim(1, rightPivotX, rightPivotY)
    }

    private fun setupRightAndLeftAnim(
        sign: Int,
        pivotX: ObjectAnimator,
        pivotY: ObjectAnimator
    ): AnimatorSet {
        val rotation = ObjectAnimator.ofFloat(
            viewBinding.employeeCard,
            "rotation",
            0f,
            45f * sign
        )
        val transition = ObjectAnimator.ofFloat(
            viewBinding.employeeCard,
            "translationX",
            viewBinding.employeeCard.translationX,
            1000f * sign
        )
        transition.duration = 300
        transition.startDelay = 200
        val fadeOut = ObjectAnimator.ofFloat(
            viewBinding.employeeCard,
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

                when (viewModel.getCVListSize()) {
                    viewModel.getIndex() + 1 -> {
                        viewBinding.employeeCard.translationX = baseTranslationX
                        viewBinding.employeeCard.translationY = baseTranslationY
                        viewBinding.employeeCard.rotation = baseRotation
                        lifecycleScope.launch {
                            if (viewModel.getIndex() + 1 >= viewModel.getCVListSize()) {
                                viewModel.loadVacancy()
                            }
                        }
                    }
                    viewModel.getIndex() + 2 -> {
                        viewModel.increaseIndex()
                        viewBinding.employeeCard.translationX = baseTranslationX
                        viewBinding.employeeCard.translationY = baseTranslationY
                        viewBinding.employeeCard.rotation = baseRotation
                        setupShownCard(viewModel.getCV(viewModel.getIndex()))
                        viewBinding.employeeCard.alpha = 1f
                        viewBinding.employeeCardNext.isVisible = false
                    }
                    else -> {
                        viewModel.increaseIndex()
                        viewBinding.employeeCard.translationX = baseTranslationX
                        viewBinding.employeeCard.translationY = baseTranslationY
                        viewBinding.employeeCard.rotation = baseRotation
                        setupShownCard(viewModel.getCV(viewModel.getIndex()))
                        viewBinding.employeeCard.alpha = 1f
                        viewBinding.employeeCardNext.alpha = 0f
                        setupNextCard(viewModel.getCV(viewModel.getIndex() + 1))
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
        return swipeAnimatorSet
    }

    private fun setupShownCard(cv: CV) {
        viewBinding.jobName.text = cv.name
        if (cv.image != null) {
            viewBinding.iconImageShown.setImageDrawable(cv.image)
        }
        viewBinding.employeeName.text = "${cv.userName} ${cv.userSurname}"
        viewBinding.employeeSalary.text = "Salary: ${cv.salary} \$ / h"
        viewBinding.employeeGenderAndAge.text = "${cv.userGender}, ${cv.userAge} years old"
        viewBinding.educationInfo.text = cv.education
        viewBinding.workInfo.text = cv.experience
        viewBinding.projectsInfo.text = cv.content
    }

    private fun setupNextCard(cv: CV) {
        viewBinding.jobNameNext.text = cv.name
        if (cv.image != null) {
            viewBinding.iconImageShownNext.setImageDrawable(cv.image)
        }
        viewBinding.employeeNameNext.text = "${cv.userName} ${cv.userSurname}"
        viewBinding.employeeSalaryNext.text = "Salary: ${cv.salary} \$ / h"
        viewBinding.employeeGenderAndAgeNext.text = "${cv.userGender}, ${cv.userAge} years old"
        viewBinding.educationInfoNext.text = cv.education
        viewBinding.workInfoNext.text = cv.experience
        viewBinding.projectsInfoNext.text = cv.content
    }
}