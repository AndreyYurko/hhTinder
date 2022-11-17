package com.andreyyurko.hhtinder.singleton

import android.util.Log
import com.andreyyurko.hhtinder.structures.Vocab
import com.andreyyurko.hhtinder.utils.network.VocabHandler
import kotlin.math.log

class VocabsSingleton {

    private var jobCategory: List<Vocab> = emptyList()

    companion object {
        const val LOG_TAG = "VocabSingleton"
        val instance = VocabsSingleton()
    }

    suspend fun initAllVocabs() {
        Log.d(LOG_TAG, "Initializing Vocabs")
        initJobCategoryVocab()
    }

    suspend fun initJobCategoryVocab() {
        jobCategory = VocabHandler.instance.getVocab("job_categories")
    }

    fun getJobCatVocab(): List<String> {
        val res = ArrayList<String>()

        res.add("Категории")

        jobCategory.forEach { res.add(it.name) }

        return res
    }
}