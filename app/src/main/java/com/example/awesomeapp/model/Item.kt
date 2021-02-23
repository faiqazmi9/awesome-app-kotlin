package com.example.awesomeapp.model

class Item {
    private var imgResId = 0
    private var title: String? = null
    private var description: String? = null

    fun Item(imgResId: Int, title: String?, description: String?) {
        this.imgResId = imgResId
        this.title = title
        this.description = description
    }

    fun getImgResId(): Int {
        return imgResId
    }

    fun getTitle(): String? {
        return title
    }

    fun getDescription(): String? {
        return description
    }
}