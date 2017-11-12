package chesire.eorzeaninfo.parsing_library.serializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import chesire.eorzeaninfo.parsing_library.models.ClassModel;

/**
 * Serializer to parse out the correct data to get a ClassModel object
 */
public class ClassModelDeserializer implements JsonDeserializer<ClassModel> {
    @Override
    public ClassModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Rip the icon out of the "data" json, as that's all we want to add in
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement iconElement = (jsonObject.get("data")).getAsJsonObject().get("icon");
        jsonObject.add("icon", iconElement);

        return new Gson().fromJson(jsonObject, ClassModel.class);
    }
}
