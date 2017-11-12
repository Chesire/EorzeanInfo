package chesire.eorzeaninfo.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chesire.eorzeaninfo.parsing_library.models.BasicCharacterModel;
import chesire.eorzeaninfo.parsing_library.models.DetailedCharacterModel;
import chesire.eorzeaninfo.interfaces.CharacterStorage;
import chesire.eorzeaninfo.interfaces.UpdateCharacterCallback;
import chesire.eorzeaninfo.interfaces.XIVDBService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Implementation of {@link CharacterStorage} that stores the character data in SharedPreferences
 */
public class SharedPreferencesCharacterStorage implements CharacterStorage {
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
            Timber.w("Character with id [%d] could not be found", id);
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
        return mSharedPreferences.getInt(PREF_CURRENT_CHARACTER_ID, CharacterStorage.NO_CHARACTER_ID);
    }

    @Override
    public ArrayList<BasicCharacterModel> getAllCharacters() {
        Set<String> allCharIds = mSharedPreferences.getStringSet(PREF_ALL_CHARACTERS, null);
        if (allCharIds == null) {
            Timber.w("No character ids found when trying to getAllCharacters");
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
            Timber.w("No character ids found when trying to getAllCharacterIds");
            return null;
        }

        List<Integer> allIds = new ArrayList<>();
        for (String charId : allCharIds) {
            allIds.add(Integer.parseInt(charId));
        }

        return allIds;
    }

    @Override
    public void updateCharacter(final int id, final UpdateCharacterCallback callback) {
        try {
            Call<DetailedCharacterModel> charCall = mXIVService.getCharacter(id);
            charCall.enqueue(new Callback<DetailedCharacterModel>() {
                @Override
                public void onResponse(Call<DetailedCharacterModel> call, Response<DetailedCharacterModel> response) {
                    Timber.i("Successfully updated character with id [%d]", id);

                    mSharedPreferences.edit()
                            .putString(String.format(PREF_CHARACTER_DATA, response.body().getId()), new Gson().toJson(response.body()))
                            .apply();
                    callback.onCharacterUpdate(response.body(), true);
                }

                @Override
                public void onFailure(Call<DetailedCharacterModel> call, Throwable t) {
                    Timber.e(t, "Error sending character update request");
                    callback.onCharacterUpdate(null, false);
                }
            });
        } catch (Exception ex) {
            Timber.e(ex, "Error sending character update request");
            callback.onCharacterUpdate(null, false);
        }
    }
}
