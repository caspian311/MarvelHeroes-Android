package net.todd.mavelheroes;

import com.google.gson.annotations.SerializedName;

public class MarvelCharacter {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;

    public MarvelCharacter(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return description;
    }
}