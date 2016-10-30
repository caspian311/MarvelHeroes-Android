package net.todd.mavelheroes.data;

public class CharacterThumbnail {
    private String path;
    private String extension;

    public String getImagePath() {
        return path + "." + extension;
    }
}
