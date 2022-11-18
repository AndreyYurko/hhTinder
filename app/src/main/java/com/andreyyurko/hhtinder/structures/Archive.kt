package com.andreyyurko.hhtinder.structures

import android.graphics.drawable.Drawable

class Archive(id: Int, name: String, content: String) {
    val id = id
    val name = name
    val content = content
    var image: Drawable? = null
}