package chesire.eorzeaninfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import chesire.eorzeaninfo.views.CharacterSelect;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Choose where to go, for now we should load into the CharacterSelect
        // Once we have a screen to create we should go there

        Intent loadActivityIntent = new Intent(this, CharacterSelect.class);
        loadActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loadActivityIntent);
    }
}
