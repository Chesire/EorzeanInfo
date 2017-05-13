package chesire.eorzeaninfo.views;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnItemSelected;
import chesire.eorzeaninfo.EorzeanInfoApp;
import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.interfaces.XIVDBService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterSearchActivity extends AppCompatActivity {
    private static String TAG = "CharacterSearchActivity";

    @Inject
    XIVDBService mXIVClient;

    @BindView(R.id.character_search_name_input)
    TextInputEditText mCharacterNameInput;
    @BindView(R.id.character_search_data_centre_selection)
    AppCompatSpinner mDataCentreSelector;
    @BindView(R.id.character_search_server_selection)
    AppCompatSpinner mCharacterServerSelector;
    @BindView(R.id.character_search_server_label)
    AppCompatTextView mCharacterServerLabel;
    @BindView(R.id.character_search_button)
    AppCompatButton mSearchButton;
    @BindView(R.id.character_search_progress_indicator)
    ProgressBar mSearchingProgress;

    @BindArray(R.array.data_centres)
    String[] mDataCentres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_search);

        ((EorzeanInfoApp) getApplication()).getXIVComponent().inject(this);
        ButterKnife.bind(this);

        ArrayAdapter<String> dataCentreAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mDataCentres);
        mDataCentreSelector.setAdapter(dataCentreAdapter);
    }

    @OnItemSelected(R.id.character_search_data_centre_selection)
    void onItemSelected() {
        String[] selectedServerList;
        String selectedDataCentre = (String) mDataCentreSelector.getSelectedItem();
        switch (selectedDataCentre) {
            case "Any":
                selectedServerList = new String[0];
                break;

            case "Elemental":
                selectedServerList = getResources().getStringArray(R.array.elemental_servers);
                break;

            case "Gaia":
                selectedServerList = getResources().getStringArray(R.array.gaia_servers);
                break;

            case "Mana":
                selectedServerList = getResources().getStringArray(R.array.mana_servers);
                break;

            case "Aether":
                selectedServerList = getResources().getStringArray(R.array.aether_servers);
                break;

            case "Primal":
                selectedServerList = getResources().getStringArray(R.array.primal_servers);
                break;

            case "Chaos":
                selectedServerList = getResources().getStringArray(R.array.chaos_servers);
                break;

            default:
                throw new IndexOutOfBoundsException(String.format("Unexpected value encountered - %1$s", selectedDataCentre));
        }

        mCharacterServerSelector.setVisibility(selectedServerList.length == 0 ? View.INVISIBLE : View.VISIBLE);
        mCharacterServerLabel.setVisibility(selectedServerList.length == 0 ? View.INVISIBLE : View.VISIBLE);
        ArrayAdapter<String> serverAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, selectedServerList);
        mCharacterServerSelector.setAdapter(serverAdapter);
    }

    @OnEditorAction(R.id.character_search_name_input)
    boolean nameInputDonePressed(int actionId) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            handled = true;
            searchClicked();
        }

        return handled;
    }

    @OnClick(R.id.character_search_button)
    void searchClicked() {
        String searchName = mCharacterNameInput.getEditableText().toString();
        if (searchName.equals("")) {
            return;
        }

        String selectedServer;
        Object selectedItem = mCharacterServerSelector.getSelectedItem();
        if (selectedItem == null) {
            selectedServer = null;
        } else {
            selectedServer = (String) selectedItem;
        }

        displayInProgressIndicator(true);

        try {
            Call<XIVDBService.SearchCharactersResponse> charCall = mXIVClient.searchCharacters(selectedServer, searchName);
            charCall.enqueue(new Callback<XIVDBService.SearchCharactersResponse>() {
                @Override
                public void onResponse(Call<XIVDBService.SearchCharactersResponse> call, Response<XIVDBService.SearchCharactersResponse> response) {
                    Log.d(TAG, "Successful search request");
                    displayInProgressIndicator(false);

                    if (response.body().characters.results.isEmpty()) {
                        Toast.makeText(CharacterSearchActivity.this, getString(R.string.search_no_characters_found), Toast.LENGTH_SHORT).show();
                    } else {
                        CharacterSearchDialogFragment searchDialog = CharacterSearchDialogFragment.newInstance(response.body().characters.results);
                        searchDialog.show(getSupportFragmentManager(), CharacterSearchDialogFragment.TAG);
                    }
                }

                @Override
                public void onFailure(Call<XIVDBService.SearchCharactersResponse> call, Throwable t) {
                    Log.e(TAG, "Error sending search request - " + t);

                    displayInProgressIndicator(false);
                    Toast.makeText(CharacterSearchActivity.this, getString(R.string.search_failed_search), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "Error sending search request - " + ex);

            displayInProgressIndicator(false);
        }
    }

    private void displayInProgressIndicator(boolean val) {
        if (val) {
            mSearchingProgress.setVisibility(View.VISIBLE);
            mSearchButton.setVisibility(View.INVISIBLE);
        } else {
            mSearchingProgress.setVisibility(View.INVISIBLE);
            mSearchButton.setVisibility(View.VISIBLE);
        }
    }
}
