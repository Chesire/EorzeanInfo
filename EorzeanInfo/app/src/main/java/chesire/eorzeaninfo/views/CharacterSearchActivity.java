package chesire.eorzeaninfo.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.classes.CharacterModel;

/**
 * Activity used for searching for a new character to use
 */
public class CharacterSearchActivity extends AppCompatActivity implements CharacterSearchFragment.CharacterSearchListener {
    private static final String CHARACTER_SEARCH_TAG = "CHARACTER_SEARCH_TAG";
    private static final String CHARACTER_SELECT_TAG = "CHARACTER_SELECT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_search);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.character_search_container, CharacterSearchFragment.newInstance(), CHARACTER_SEARCH_TAG)
                    .commit();
        }
    }

    @Override
    public void onCharactersFound(ArrayList<CharacterModel> models) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.character_search_container, CharacterSelectFragment.newInstance(models), CHARACTER_SELECT_TAG)
                .addToBackStack(null)
                .commit();
    }
}
