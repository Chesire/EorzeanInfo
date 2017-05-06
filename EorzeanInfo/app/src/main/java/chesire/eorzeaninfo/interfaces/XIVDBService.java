package chesire.eorzeaninfo.interfaces;

import java.util.List;

import chesire.eorzeaninfo.classes.CharacterModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface XIVDBService {
    @GET("search")
    Call<List<CharacterModel>> searchUsers();
}
