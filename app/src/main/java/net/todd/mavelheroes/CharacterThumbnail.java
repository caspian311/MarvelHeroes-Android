package net.todd.mavelheroes;

import com.google.gson.annotations.SerializedName;

public class CharacterThumbnail {
    @SerializedName("path")
    String path;
    @SerializedName("extension")
    String extension;

    public CharacterThumbnail(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    public String getImagePath() {
        return path + "." + extension;
    }
}
