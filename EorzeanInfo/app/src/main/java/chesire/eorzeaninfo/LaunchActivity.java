package chesire.eorzeaninfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import chesire.eorzeaninfo.views.CharacterSearchActivity;

public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Choose where to go, for now we should load into the CharacterSearch
        // Once we have a screen to choose we should go there

        Intent loadActivityIntent = new Intent(this, CharacterSearchActivity.class);
        loadActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loadActivityIntent);
        finish();
    }
}
