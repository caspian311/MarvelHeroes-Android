package net.todd.mavelheroes.data;

public class MarvelComic {
    private String title;
    private String id;
    private CharacterThumbnail thumbnail;

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail.getImagePath();
    }

    public String getId() {
        return id;
    }
}
