package net.todd.mavelheroes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelService {
    @GET("characters")
    Call<MarvelCharacterResponse> listCharacters(@Query("limit") int limit, @Query("offset") int offset);

    @GET("characters/{character_id}")
    Call<MarvelCharacterResponse> getCharacter(@Path("character_id") String characterId);
}