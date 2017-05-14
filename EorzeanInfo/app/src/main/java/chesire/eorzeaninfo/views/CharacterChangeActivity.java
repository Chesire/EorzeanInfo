package chesire.eorzeaninfo.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import chesire.eorzeaninfo.EorzeanInfoApp;
import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.classes.CharacterModel;
import chesire.eorzeaninfo.interfaces.CharacterStorage;

/**
 * Activity used to change the currently selected character
 */
public class CharacterChangeActivity extends AppCompatActivity
        implements CharacterSelectFragment.CharacterSelectListener, CharacterSearchFragment.CharacterSearchListener {
    private static final String CHARACTER_SEARCH_TAG = "CHARACTER_SEARCH_TAG";
    private static final String CHARACTER_SELECT_TAG = "CHARACTER_SELECT_TAG";

    @Inject
    CharacterStorage mCharacterStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_change);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((EorzeanInfoApp) getApplication()).getCharacterStorageComponent().inject(this);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.character_change_container, CharacterSelectFragment.newInstance(mCharacterStorage.getAllCharacters()), CHARACTER_SELECT_TAG)
                    .commit();
        }
    }

    @OnClick(R.id.character_select_fab)
    void onFabClicked() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.character_change_container, CharacterSearchFragment.newInstance(), CHARACTER_SEARCH_TAG)
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

    @Override
    public void onCharactersFound(ArrayList<CharacterModel> models) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.character_change_container, CharacterSelectFragment.newInstance(models), CHARACTER_SELECT_TAG)
                .addToBackStack(null)
                .commit();
    }
}
