package chesire.eorzeaninfo.interfaces;

import java.util.ArrayList;

import chesire.eorzeaninfo.parsing_library.models.BasicCharacterModel;
import chesire.eorzeaninfo.parsing_library.models.DetailedCharacterModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface XIVDBService {
    String SERVICE_ENDPOINT = "https://api.xivdb.com";

    @GET("search?order_field=name&order_direction=asc&one=characters&limit=60&strict=on")
    Call<SearchCharactersResponse> searchCharacters(@Query("server|et") String server, @Query("string") String characterName);

    @GET("character/{id}")
    Call<DetailedCharacterModel> getCharacter(@Path("id") int characterId);

    /**
     * Tells XIVDB to add a character to its database, needs to first be added to https://xivsync.com/
     *
     * @param characterId ID of the character to add to XIVDB, acquired using the call to https://xivsync.com
     * @return Response object showing if the call was successful
     */
    @GET("character/add")
    Call<AddCharacterToXIVDBResponse> addCharacterToXIVDB(@Query("id") int characterId);

    class SearchCharactersResponse {
        public Characters characters;

        public class Characters {
            public ArrayList<BasicCharacterModel> results;
        }
    }

    class AddCharacterToXIVDBResponse {
        public boolean success;
        public String message;
    }
}
