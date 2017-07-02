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
        public boolean success;
        public XIVSyncData data;

        public class XIVSyncData {
            public String total;
            public int count;
            public ArrayList<XIVSyncCharacterData> results;
        }

        public class XIVSyncCharacterData {
            public String id;
            public String avatar;
            public String name;
            public String server;
        }
    }
}
