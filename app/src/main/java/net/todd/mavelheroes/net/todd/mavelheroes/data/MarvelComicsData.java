package net.todd.mavelheroes.net.todd.mavelheroes.data;

import java.util.List;

public class MarvelComicsData {
    private List<MarvelComic> results;

    public MarvelComicsData() {}

    public MarvelComicsData(List<MarvelComic> results) {
        this.results = results;
    }

    public List<MarvelComic> getResults() {
        return results;
    }

    public void setResults(List<MarvelComic> results) {
        this.results = results;
    }
}
