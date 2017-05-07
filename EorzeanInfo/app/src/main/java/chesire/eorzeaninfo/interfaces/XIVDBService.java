package chesire.eorzeaninfo.interfaces;

import java.util.List;

import chesire.eorzeaninfo.classes.CharacterModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface XIVDBService {
    String SERVICE_ENDPOINT = "https://api.xivdb.com";

    @GET("search?order_field=name&order_direction=asc&one=characters&limit=60&strict=on")
    Call<SearchCharactersResponse> searchCharacters(@Query("server|et") String server, @Query("string") String characterName);

    class SearchCharactersResponse {
        public Characters characters;

        class Characters {
            public List<CharacterModel> results;
        }
    }
}
