package net.todd.mavelheroes.net.todd.mavelheroes.data

import com.google.gson.annotations.SerializedName

data class MarvelCharacterData(@SerializedName("results")
                          var results: List<MarvelCharacter>)

