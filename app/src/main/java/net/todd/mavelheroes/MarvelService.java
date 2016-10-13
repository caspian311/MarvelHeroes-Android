package net.todd.mavelheroes;

import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelCharacterResponse;
import net.todd.mavelheroes.net.todd.mavelheroes.data.MarvelComicsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MarvelService {
    @GET("characters/{character_id}")
    Call<MarvelCharacterResponse> getCharacter(@Path("character_id") String characterId);

    @GET("comics")
    Call<MarvelComicsResponse> getComics();
}