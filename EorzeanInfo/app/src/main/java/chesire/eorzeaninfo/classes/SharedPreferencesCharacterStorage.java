package chesire.eorzeaninfo.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

import chesire.eorzeaninfo.interfaces.CharacterStorage;

/**
 * Implementation of {@link CharacterStorage} that stores the character data in SharedPreferences
 */
public class SharedPreferencesCharacterStorage implements CharacterStorage {
    private static String PREF_CURRENT_CHARACTER_ID = "PREF_CURRENT_CHARACTER_ID";
    private static String PREF_ALL_CHARACTERS_ARRAY = "PREF_ALL_CHARACTERS_ARRAY";

    private SharedPreferences mSharedPreferences;

    /**
     * Default constructor
     *
     * @param context Context used to get the instance of the SharedPreferences
     */
    public SharedPreferencesCharacterStorage(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void addCharacter(CharacterModel model) {
        boolean firstCharacter = false;
        Set<String> characters = mSharedPreferences.getStringSet(PREF_ALL_CHARACTERS_ARRAY, null);

        if (characters == null) {
            firstCharacter = true;
            characters = new HashSet<>();
        }

        characters.add(new Gson().toJson(model));
        mSharedPreferences.edit()
                .putStringSet(PREF_ALL_CHARACTERS_ARRAY, characters)
                .apply();
        if (firstCharacter) {
            setCurrentCharacter(model.getId());
        }
    }

    @Override
    public CharacterModel getCharacter(int id) {
        Set<String> characters = mSharedPreferences.getStringSet(PREF_ALL_CHARACTERS_ARRAY, null);
        if (characters == null) {
            return null;
        }

        CharacterModel foundCharacter = null;
        Gson gsonConverter = new Gson();
        for (String characterJson : characters) {
            CharacterModel model = gsonConverter.fromJson(characterJson, CharacterModel.class);

            if (model.getId() == id) {
                foundCharacter = model;
                break;
            }
        }

        return foundCharacter;
    }

    @Override
    public void setCurrentCharacter(int id) {
        mSharedPreferences.edit()
                .putInt(PREF_CURRENT_CHARACTER_ID, id)
                .apply();
    }

    @Override
    public int getCurrentCharacter() {
        return mSharedPreferences.getInt(PREF_CURRENT_CHARACTER_ID, 0);
    }
}
