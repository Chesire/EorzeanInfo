package chesire.eorzeaninfo.interfaces;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface XIVSyncService {

    String SERVICE_ENDPOINT = "https://xivsync.com";

    @GET("character/search")
    Call<XIVSyncCharacterResponse> syncCharacter(@Query("server") String server, @Query("name") String characterName);

    class XIVSyncCharacterResponse {
        boolean success;
        XIVSyncData data;

        private class XIVSyncData {
            String total;
            int count;
            ArrayList<XIVSyncCharacterData> results;
        }

        private class XIVSyncCharacterData {
            String id;
            String avatar;
            String name;
            String server;
        }
    }
}
