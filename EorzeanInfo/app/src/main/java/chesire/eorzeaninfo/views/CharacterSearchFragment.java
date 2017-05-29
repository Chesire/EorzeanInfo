package chesire.eorzeaninfo.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

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
import chesire.eorzeaninfo.parsing_library.models.BasicCharacterModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterSearchFragment extends Fragment {
    private static String TAG = "CharacterSearchFragment";

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

    private CharacterSearchListener mListener;

    /**
     * Generates a new instance of {@link CharacterSearchFragment}
     *
     * @return New instance of the {@link CharacterSearchFragment}
     */
    public static CharacterSearchFragment newInstance() {
        return new CharacterSearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_character_search, container, false);

        ((EorzeanInfoApp) getActivity().getApplication()).getXIVComponent().inject(this);
        ButterKnife.bind(this, v);

        ArrayAdapter<String> dataCentreAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, mDataCentres);
        mDataCentreSelector.setAdapter(dataCentreAdapter);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof CharacterSearchListener) {
            mListener = (CharacterSearchListener) context;
        } else {
            throw new ClassCastException("CharacterSearchFragment - Context was not instance of CharacterSearchListener");
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
        mCharacterServerLabel.setVisibility(selectedServerList.length == 0 ? View.INVISIBLE : View.VISIBLE);
        ArrayAdapter<String> serverAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, selectedServerList);
        mCharacterServerSelector.setAdapter(serverAdapter);
    }

    @OnEditorAction(R.id.character_search_name_input)
    boolean nameInputDonePressed(int actionId) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            handled = true;
            hideSoftKeyboard();
            searchClicked();
        }

        return handled;
    }

    @OnClick(R.id.character_search_button)
    void searchClicked() {
        String searchName = mCharacterNameInput.getEditableText().toString().trim();
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
                        Toast.makeText(getContext(), getString(R.string.search_no_characters_found), Toast.LENGTH_SHORT).show();
                    } else {
                        mListener.onCharactersFound(response.body().characters.results);
                    }
                }

                @Override
                public void onFailure(Call<XIVDBService.SearchCharactersResponse> call, Throwable t) {
                    Log.e(TAG, "Error sending search request - " + t);

                    displayInProgressIndicator(false);
                    Toast.makeText(getContext(), getString(R.string.search_failed_search), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "Error sending search request - " + ex);

            displayInProgressIndicator(false);
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        View focus = getActivity().getCurrentFocus();
        inputManager.hideSoftInputFromWindow(focus == null ? null :
                getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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

    /**
     * Interface to callback to the container Activity when characters have been found
     */
    interface CharacterSearchListener {
        /**
         * Executed when the list of characters is found
         *
         * @param models List of generated character models
         */
        void onCharactersFound(ArrayList<BasicCharacterModel> models);
    }
}
