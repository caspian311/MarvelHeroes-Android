package net.todd.mavelheroes

import com.google.gson.annotations.SerializedName

data class MarvelCharacterResponse(@SerializedName("data")
                              var data: MarvelCharacterData)
