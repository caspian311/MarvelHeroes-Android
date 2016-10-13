package net.todd.mavelheroes.net.todd.mavelheroes.data

import com.google.gson.annotations.SerializedName

class CharacterThumbnail(@SerializedName("path")
                         internal var path: String, @SerializedName("extension")
                         internal var extension: String) {

    val imagePath: String
        get() = path + "." + extension
}
