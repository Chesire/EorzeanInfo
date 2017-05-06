package chesire.eorzeaninfo.interfaces;

import java.util.List;

import chesire.eorzeaninfo.classes.CharacterModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface XIVDBService {
    @GET("search?order_field=name&order_direction=asc&one=characters&limit=60&strict=off")
    Call<SearchCharactersResponse> searchCharacters(@Query("string") String string);

    class SearchCharactersResponse {
        public Characters characters;

        public class Characters {
            public List<CharacterModel> results;
        }
    }
}
