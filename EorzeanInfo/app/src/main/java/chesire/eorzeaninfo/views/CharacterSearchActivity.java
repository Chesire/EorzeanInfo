package chesire.eorzeaninfo.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import chesire.eorzeaninfo.EorzeanInfoApp;
import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.classes.CharacterModel;
import chesire.eorzeaninfo.interfaces.CharacterStorage;

/**
 * Activity used for searching for a new character to use
 */
public class CharacterSearchActivity extends AppCompatActivity
        implements CharacterSearchFragment.CharacterSearchListener, CharacterSelectFragment.CharacterSelectListener {
    private static final String CHARACTER_SEARCH_TAG = "CHARACTER_SEARCH_TAG";
    private static final String CHARACTER_SELECT_TAG = "CHARACTER_SELECT_TAG";

    @Inject
    CharacterStorage mCharacterStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_search);

        ((EorzeanInfoApp) getApplication()).getCharacterStorageComponent().inject(this);

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

    @Override
    public void onCharacterSelected(CharacterModel model) {
        mCharacterStorage.addCharacter(model);
        Intent loadProfileIntent = new Intent(this, CharacterProfileActivity.class);
        loadProfileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loadProfileIntent);
    }
}
