package chesire.eorzeaninfo.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import chesire.eorzeaninfo.interfaces.CharacterStorage;

public class SharedPreferencesCharacterStorage implements CharacterStorage {

    private SharedPreferences mSharedPreferences;

    public SharedPreferencesCharacterStorage(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void addCharacter(CharacterModel model) {

    }

    @Override
    public CharacterModel getCharacter(int id) {
        return null;
    }

    @Override
    public void setCurrentCharacter(CharacterModel model) {

    }

    @Override
    public int getCurrentCharacter() {
        return 0;
    }
}
