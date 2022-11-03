package com.andreyyurko.hhtinder.data

import android.graphics.drawable.Drawable

data class EmployeeInfo(
    val jobName: String,
    val icon: Drawable?,
    val name: String,
    val salary: String,
    val gender: String,
    val age: String,
    val education: String,
    val work: String,
    val projects: String
)