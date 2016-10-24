package net.todd.mavelheroes.net.todd.mavelheroes.data;

public class MarvelComic {
    private String id;
    private String title;
    private MarvelComicThumnbail thumbnail;

    public MarvelComic() {}

    public MarvelComic(String id, String title, MarvelComicThumnbail thumbnail) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MarvelComicThumnbail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MarvelComicThumnbail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailPath() {
        return getThumbnail().getPath() + "." + getThumbnail().getExtension();
    }
}
