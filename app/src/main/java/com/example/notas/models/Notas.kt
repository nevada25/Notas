package com.example.notas.models

class Notes {
    var id: Int = 0
    var title: String? = null
    var description: String? = null

    constructor(id: Int, title: String, description: String) {
        this.id = id
        this.title = title
        this.description = description
    }

    constructor(title: String, description: String) {
        this.title = title
        this.description = description
    }
}