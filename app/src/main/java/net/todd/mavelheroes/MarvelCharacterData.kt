package net.todd.mavelheroes

import com.google.gson.annotations.SerializedName

data class MarvelCharacterData(@SerializedName("results")
                          var results: Array<MarvelCharacter>)

