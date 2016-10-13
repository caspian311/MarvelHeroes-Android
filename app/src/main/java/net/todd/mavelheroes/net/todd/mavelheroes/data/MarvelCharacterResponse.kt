package net.todd.mavelheroes.net.todd.mavelheroes.data

import com.google.gson.annotations.SerializedName

data class MarvelCharacterResponse(@SerializedName("data")
                              var data: MarvelCharacterData)
