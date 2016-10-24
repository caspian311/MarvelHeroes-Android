package net.todd.mavelheroes.net.todd.mavelheroes.data;

public class MarvelComicThumnbail {
    private String path;
    private String extension;

    public MarvelComicThumnbail() {}

    public MarvelComicThumnbail(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
