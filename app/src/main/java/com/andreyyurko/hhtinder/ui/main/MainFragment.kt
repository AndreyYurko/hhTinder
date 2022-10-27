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
import androidx.lifecycle.viewModelScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentMainBinding
import com.andreyyurko.hhtinder.structures.Vacancy
import com.andreyyurko.hhtinder.utils.network.VacancyHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainFragment : Fragment(R.layout.fragment_main) {
    companion object {
        const val LOG_TAG = "Main Fragment"
    }

    private lateinit var imagesList: MutableList<Drawable?>
    private var index = 0
    private var baseTranslationX = 0F
    private var baseTranslationY = 0F
    private var baseRotation = 0F

    private var vacancyHandler = VacancyHandler()

    private var vacancyList = arrayListOf<Vacancy>()

    private val viewBinding by viewBinding(FragmentMainBinding::bind)


    // TODO: В будущем оно должно будет возвращать не вот этот колхоз
    fun loadVacancy() {
        runBlocking {
            launch {
                for(i in 0 .. 20){
                    val vac = vacancyHandler.getNextVacancy()
                    vacancyList.add(vac)
                    Log.d(LOG_TAG, vac.name)
                }
                var lastVac = vacancyHandler.getNextVacancy()
                setVacancyContent(lastVac)
            }
        }
    }

    fun setVacancyContent(vac : Vacancy){
        viewBinding.jobName.setText(vac.name)
        viewBinding.projectsInfo.setText(vac.content)

        Log.d(LOG_TAG, vac.name)
    }

    fun setNextVacancy(){
        if(index < vacancyList.size){
            val vacancy = vacancyList.get(index)
            setVacancyContent(vacancy)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadVacancy()

        baseRotation = viewBinding.employeeCard.rotation
        baseTranslationX = viewBinding.employeeCard.translationX
        baseTranslationY = viewBinding.employeeCard.translationY

        imagesList = context?.let {
            mutableListOf(
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

        /*viewBinding.iconImageNext.setImageDrawable(
            imagesList[index+1]
        )
        viewBinding.iconImageNext.alpha = 0f*/

        val swipeLeftAnimatorSet = setupLeftSwipeAnim()
        val swipeRightAnimatorSet = setupRightSwipeAnim()
        /*val fadeIn = ObjectAnimator.ofFloat(
            viewBinding.iconImageNext,
            "alpha",
            0f,
            1f
        )
        fadeIn.duration = 500*/

        viewBinding.employeeCard.setOnTouchListener(object :
            GesturesHandler(context, viewBinding.employeeCard) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                swipeLeftAnimatorSet.start()
                //fadeIn.start()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                swipeRightAnimatorSet.start()
                //fadeIn.start()
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
                index += 1
                Log.d(MainFragment.LOG_TAG, "index = $index")
                setNextVacancy()
                if (index + 1 < (imagesList.size)) {
                    viewBinding.employeeCard.translationX = baseTranslationX
                    viewBinding.employeeCard.translationY = baseTranslationY
                    viewBinding.employeeCard.rotation = baseRotation
                    viewBinding.iconImageShown.setImageDrawable(
                        imagesList[index]
                    )
                    viewBinding.employeeCard.alpha = 1f
                    //viewBinding.iconImageNext.alpha = 0f
                    //viewBinding.iconImageNext.setImageDrawable(
                    //    imagesList[index+1]
                    //)
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
    }
}