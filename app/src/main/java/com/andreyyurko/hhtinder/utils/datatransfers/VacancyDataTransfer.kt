package com.andreyyurko.hhtinder.utils.datatransfers

import android.graphics.drawable.Drawable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VacancyDataTransfer @Inject constructor() {

    var id: Int = 0
    var icon: Drawable? = null
    var vacancyName: String = ""
    var vacancyText: String = ""
    var date: String = ""
    var category: String = ""


}