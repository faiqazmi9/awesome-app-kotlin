package com.example.awesomeapp.model

class Item {
    var id = 0
    var width = 0
    var height = 0
    var url: String? = null
    var photographer: String? = null
    var photographerUrl: String? = null
    var photographerId = 0
    var avgColor: String? = null
    var original: String? = null
    var large2x: String? = null
    var large: String? = null
    var medium: String? = null
    var small: String? = null
    var portrait: String? = null
    var landscape: String? = null
    var tiny: String? = null

    constructor() {}
    constructor(
        id: Int,
        width: Int,
        height: Int,
        url: String?,
        photographer: String?,
        photographerUrl: String?,
        photographerId: Int,
        avgColor: String?,
        original: String?,
        large2x: String?,
        large: String?,
        medium: String?,
        small: String?,
        portrait: String?,
        landscape: String?,
        tiny: String?
    ) {
        this.id = id
        this.width = width
        this.height = height
        this.url = url
        this.photographer = photographer
        this.photographerUrl = photographerUrl
        this.photographerId = photographerId
        this.avgColor = avgColor
        this.original = original
        this.large2x = large2x
        this.large = large
        this.medium = medium
        this.small = small
        this.portrait = portrait
        this.landscape = landscape
        this.tiny = tiny
    }
}