package chesire.eorzeaninfo.parsing_library.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import chesire.eorzeaninfo.parsing_library.models.CharacterDataModel;
import chesire.eorzeaninfo.parsing_library.models.ClassModel;

/**
 * Serializer to parse out the correct data to get a CharacterDataModel object
 */
public class CharacterDataModelDeserializer implements JsonDeserializer<CharacterDataModel> {
    @Override
    public CharacterDataModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ClassModel.class, new ClassModelDeserializer())
                .create();

        Set<Map.Entry<String, JsonElement>> classJobsList = (json.getAsJsonObject().get("classjobs")).getAsJsonObject().entrySet();
        JsonArray classes = new JsonArray();
        for (Map.Entry<String, JsonElement> currentClass : classJobsList) {
            classes.add(currentClass.getValue());
        }

        /*
        * Get all mounts & minions as a list of IDs,
        * then we can just compare the IDS against the full list
        */
        Set<Map.Entry<String, JsonElement>> mountsList = (json.getAsJsonObject().get("mounts")).getAsJsonObject().entrySet();
        JsonArray mounts = new JsonArray();
        for (Map.Entry<String, JsonElement> currentMount : mountsList) {
            mounts.add(currentMount.getValue().getAsJsonObject().get("id"));
        }

        Set<Map.Entry<String, JsonElement>> minionsList = (json.getAsJsonObject().get("minions")).getAsJsonObject().entrySet();
        JsonArray minions = new JsonArray();
        for (Map.Entry<String, JsonElement> currentMinion : minionsList) {
            minions.add(currentMinion.getValue().getAsJsonObject().get("id"));
        }

        json.getAsJsonObject().add("classjobs", classes);
        json.getAsJsonObject().add("mounts", mounts);
        json.getAsJsonObject().add("minions", minions);

        return gson.fromJson(json, CharacterDataModel.class);
    }
}
