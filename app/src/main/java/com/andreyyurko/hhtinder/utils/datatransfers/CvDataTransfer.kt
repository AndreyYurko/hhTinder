package com.andreyyurko.hhtinder.utils.datatransfers

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CvDataTransfer @Inject constructor() {

    var cvName: String = ""
    var cvText: String = ""
    var experience: String = ""
    var education: String = ""
    var date: String = ""
    var category: String = ""

}