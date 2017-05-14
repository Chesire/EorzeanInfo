package chesire.eorzeaninfo.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chesire.eorzeaninfo.interfaces.CharacterStorage;

/**
 * Implementation of {@link CharacterStorage} that stores the character data in SharedPreferences
 */
public class SharedPreferencesCharacterStorage implements CharacterStorage {
    private static String PREF_CURRENT_CHARACTER_ID = "PREF_CURRENT_CHARACTER_ID";
    private static String PREF_CHARACTER_DATA = "PREF_CHARACTER_DATA_%1$s";
    private static String PREF_ALL_CHARACTERS = "PREF_ALL_CHARACTERS";

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
        mSharedPreferences.edit()
                .putString(String.format(PREF_CHARACTER_DATA, model.getId()), new Gson().toJson(model))
                .apply();

        Set<String> currentCharacters = mSharedPreferences.getStringSet(PREF_ALL_CHARACTERS, null);
        HashSet<String> newCharacterSet;
        if (currentCharacters == null) {
            newCharacterSet = new HashSet<>();
        } else {
            newCharacterSet = new HashSet<>(currentCharacters);
        }
        newCharacterSet.add(String.valueOf(model.getId()));
        mSharedPreferences.edit()
                .putStringSet(PREF_ALL_CHARACTERS, newCharacterSet)
                .apply();

        setCurrentCharacter(model.getId());
    }

    @Override
    public CharacterModel getCharacter(int id) {
        String character = mSharedPreferences.getString(String.format(PREF_CHARACTER_DATA, id), null);
        if (character == null) {
            return null;
        }

        return new Gson().fromJson(character, CharacterModel.class);
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

    @Override
    public ArrayList<CharacterModel> getAllCharacters() {
        Set<String> allCharIds = mSharedPreferences.getStringSet(PREF_ALL_CHARACTERS, null);
        if (allCharIds == null) {
            return null;
        }

        ArrayList<CharacterModel> allModels = new ArrayList<>();
        for (String charId : allCharIds) {
            int id = Integer.parseInt(charId);
            allModels.add(getCharacter(id));
        }

        return allModels;
    }

    @Override
    public List<Integer> getAllCharacterIds() {
        Set<String> allCharIds = mSharedPreferences.getStringSet(PREF_ALL_CHARACTERS, null);
        if (allCharIds == null) {
            return null;
        }

        List<Integer> allIds = new ArrayList<>();
        for (String charId : allCharIds) {
            allIds.add(Integer.parseInt(charId));
        }

        return allIds;
    }
}
