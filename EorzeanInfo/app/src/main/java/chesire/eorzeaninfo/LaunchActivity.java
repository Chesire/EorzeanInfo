package chesire.eorzeaninfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import chesire.eorzeaninfo.interfaces.CharacterStorage;
import chesire.eorzeaninfo.views.CharacterProfileActivity;
import chesire.eorzeaninfo.views.CharacterSearchActivity;

public class LaunchActivity extends Activity {

    @Inject
    CharacterStorage mCharacterStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((EorzeanInfoApp) getApplication()).getCharacterStorageComponent().inject(this);

        Intent loadActivityIntent;
        if (mCharacterStorage.getCurrentCharacter() == 0) {
            loadActivityIntent = new Intent(this, CharacterSearchActivity.class);
        } else {
            loadActivityIntent = new Intent(this, CharacterProfileActivity.class);
        }

        loadActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loadActivityIntent);
        finish();
    }
}
