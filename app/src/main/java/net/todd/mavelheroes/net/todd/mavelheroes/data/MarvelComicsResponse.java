package net.todd.mavelheroes.net.todd.mavelheroes.data;

public class MarvelComicsResponse {
    private MarvelComicsData data;

    public MarvelComicsResponse() {
    }

    public MarvelComicsResponse(MarvelComicsData data) {
        this.data = data;
    }

    public MarvelComicsData getData() {
        return data;
    }

    public void setData(MarvelComicsData data) {
        this.data = data;
    }
}
