package net.todd.mavelheroes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelService {
    @GET("characters/{character_id}")
    Call<MarvelCharacterResponse> getCharacter(@Path("character_id") String characterId, @Query("apikey") String apiKey, @Query("ts") String timestamp, @Query("hash") String hash);
}