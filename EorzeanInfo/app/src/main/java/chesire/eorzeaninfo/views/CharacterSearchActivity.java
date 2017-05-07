package chesire.eorzeaninfo.views;

import android.os.Bundle;
import android.support.design.BuildConfig;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import chesire.eorzeaninfo.EorzeanInfoApp;
import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.interfaces.XIVDBService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterSearchActivity extends AppCompatActivity {
    @Inject
    XIVDBService mXIVClient;

    @BindView(R.id.character_search_name_input)
    TextInputEditText mCharacterNameInput;
    @BindView(R.id.character_search_data_centre_selection)
    AppCompatSpinner mDataCentreSelector;
    @BindView(R.id.character_search_server_selection)
    AppCompatSpinner mCharacterServerSelector;

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

        if (BuildConfig.DEBUG) {
            mCharacterNameInput.setText("Cheshire Cat");
        }
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
        ArrayAdapter<String> serverAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, selectedServerList);
        mCharacterServerSelector.setAdapter(serverAdapter);
    }

    @OnClick(R.id.character_search_button)
    void searchClicked() {
        String selectedServer;
        Object selectedItem = mCharacterServerSelector.getSelectedItem();
        if (selectedItem == null) {
            selectedServer = null;
        } else {
            selectedServer = (String) selectedItem;
        }

        try {
            Call<XIVDBService.SearchCharactersResponse> charCall = mXIVClient.searchCharacters(selectedServer, mCharacterNameInput.getEditableText().toString());
            charCall.enqueue(new Callback<XIVDBService.SearchCharactersResponse>() {
                @Override
                public void onResponse(Call<XIVDBService.SearchCharactersResponse> call, Response<XIVDBService.SearchCharactersResponse> response) {
                    String s = "";
                }

                @Override
                public void onFailure(Call<XIVDBService.SearchCharactersResponse> call, Throwable t) {
                    String s = "";
                }
            });
        } catch (Exception ex) {
            String t = "";
        }
    }
}
