package chesire.eorzeaninfo.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chesire.eorzeaninfo.classes.models.BasicCharacterModel;
import chesire.eorzeaninfo.classes.models.DetailedCharacterModel;
import chesire.eorzeaninfo.interfaces.CharacterStorage;
import chesire.eorzeaninfo.interfaces.XIVDBService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of {@link CharacterStorage} that stores the character data in SharedPreferences
 */
public class SharedPreferencesCharacterStorage implements CharacterStorage {
    private static final String TAG = "CharacterStorage";

    private static String PREF_CURRENT_CHARACTER_ID = "PREF_CURRENT_CHARACTER_ID";
    private static String PREF_CHARACTER_DATA = "PREF_CHARACTER_DATA_%1$s";
    private static String PREF_ALL_CHARACTERS = "PREF_ALL_CHARACTERS";

    private SharedPreferences mSharedPreferences;
    private XIVDBService mXIVService;

    /**
     * Default constructor
     *
     * @param context Context used to get the instance of the SharedPreferences
     */
    public SharedPreferencesCharacterStorage(Context context, XIVDBService xivService) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mXIVService = xivService;
    }

    @Override
    public void addCharacter(BasicCharacterModel model) {
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
    public DetailedCharacterModel getCharacter(int id) {
        String character = mSharedPreferences.getString(String.format(PREF_CHARACTER_DATA, id), null);
        if (character == null) {
            return null;
        }

        return new Gson().fromJson(character, DetailedCharacterModel.class);
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
    public ArrayList<BasicCharacterModel> getAllCharacters() {
        Set<String> allCharIds = mSharedPreferences.getStringSet(PREF_ALL_CHARACTERS, null);
        if (allCharIds == null) {
            return null;
        }

        ArrayList<BasicCharacterModel> allModels = new ArrayList<>();
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

    @Override
    public void updateCharacter(int id) {
        try {
            Call<DetailedCharacterModel> charCall = mXIVService.getCharacter(id);
            charCall.enqueue(new Callback<DetailedCharacterModel>() {
                @Override
                public void onResponse(Call<DetailedCharacterModel> call, Response<DetailedCharacterModel> response) {
                    mSharedPreferences.edit()
                            .putString(String.format(PREF_CHARACTER_DATA, response.body().getId()), new Gson().toJson(response.body()))
                            .apply();

                    // DEBUG read back out
                    String res = mSharedPreferences.getString(String.format(PREF_CHARACTER_DATA, response.body().getId()), null);
                    DetailedCharacterModel d = new Gson().fromJson(res, DetailedCharacterModel.class);
                    String t = "";
                }

                @Override
                public void onFailure(Call<DetailedCharacterModel> call, Throwable t) {
                    String s = "";
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "Error sending character request - " + ex);
        }
    }
}
