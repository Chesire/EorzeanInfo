package chesire.eorzeaninfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

import javax.inject.Inject;

import chesire.eorzeaninfo.interfaces.CharacterStorage;
import chesire.eorzeaninfo.views.CharacterChangeActivity;
import chesire.eorzeaninfo.views.CharacterDetailsActivity;
import chesire.eorzeaninfo.views.FirstLaunchActivity;

/**
 * Activity used to choose which activity to launch on application start
 */
public class LaunchActivity extends Activity {

    @Inject
    CharacterStorage mCharacterStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((EorzeanInfoApp) getApplication()).getCharacterStorageComponent().inject(this);

        Intent loadActivityIntent;
        if (mCharacterStorage.getCurrentCharacter() == CharacterStorage.NO_CHARACTER_ID) {
            if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.SHARED_PREFERENCE_FIRST_LAUNCH_COMPLETED), false)) {
                loadActivityIntent = new Intent(this, CharacterChangeActivity.class);
                loadActivityIntent.putExtra(CharacterChangeActivity.LOAD_INTO_SEARCH_TAG, true);
            } else {
                loadActivityIntent = new Intent(this, FirstLaunchActivity.class);
            }
        } else {
            loadActivityIntent = new Intent(this, CharacterDetailsActivity.class);
        }

        loadActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loadActivityIntent);
        finish();
    }
}
