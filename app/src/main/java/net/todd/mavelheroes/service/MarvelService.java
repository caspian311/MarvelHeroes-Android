package net.todd.mavelheroes.service;

import net.todd.mavelheroes.data.MarvelCharacterResponse;
import net.todd.mavelheroes.data.MarvelComicsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface MarvelService {
    @GET("characters/{character_id}")
    Observable<MarvelCharacterResponse> getCharacter(@Path("character_id") String characterId);

    @GET("series/{commic_id}/characters")
    Observable<MarvelCharacterResponse> getCharacterForComic(@Path("commic_id") String comicId);

    @GET("series")
    Observable<MarvelComicsResponse> getComics(@Query("characters")String characterId);
}