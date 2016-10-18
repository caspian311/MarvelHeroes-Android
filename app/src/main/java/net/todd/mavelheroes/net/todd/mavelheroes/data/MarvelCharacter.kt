package net.todd.mavelheroes.net.todd.mavelheroes.data

class MarvelCharacter {
    var id: String? = null
    var name: String? = null
    var description: String? = null
    var thumbnail: CharacterThumbnail? = null

    constructor() {
    }

    constructor(id: String, name: String, description: String, thumbnail: CharacterThumbnail) {
        this.id = id
        this.name = name
        this.description = description
        this.thumbnail = thumbnail
    }

    val imagePath: String
        get() = thumbnail!!.imagePath
}
