package net.todd.mavelheroes;


import com.google.gson.annotations.SerializedName;

public class MarvelCharacterData {
    @SerializedName("results")
    MarvelCharacter[] results;

    public MarvelCharacterData(MarvelCharacter[] results) {
        this.results = results;
    }
}

