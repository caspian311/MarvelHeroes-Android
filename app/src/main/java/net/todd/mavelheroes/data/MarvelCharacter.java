package net.todd.mavelheroes.data;

public class MarvelCharacter {
    private String description;
    private String name;
    private String id;
    private CharacterThumbnail thumbnail;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return thumbnail.getImagePath();
    }

    public String getId() {
        return id;
    }
}
