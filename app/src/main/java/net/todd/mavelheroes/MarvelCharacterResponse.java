package net.todd.mavelheroes;


import com.google.gson.annotations.SerializedName;

public class MarvelCharacterResponse {
    @SerializedName("data")
    MarvelCharacterData data;

    public MarvelCharacterResponse(MarvelCharacterData data) {
        this.data = data;
    }
}
