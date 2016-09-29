package net.todd.mavelheroes

import com.google.gson.annotations.SerializedName

class MarvelCharacter(@SerializedName("id")
                      internal var id: String, name: String, description: String) {
    @SerializedName("name")
    var name: String
        internal set
    @SerializedName("description")
    var bio: String
        internal set
    @SerializedName("thumbnail")
    internal var thumbnail: CharacterThumbnail? = null

    init {
        this.name = name
        this.bio = description
    }

    val imagePath: String
        get() = thumbnail!!.imagePath
}