package chesire.eorzeaninfo.views;

import android.os.Bundle;
import android.support.design.BuildConfig;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.character_search_server_input)
    TextInputEditText mCharacterServerInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_search);

        ((EorzeanInfoApp) getApplication()).getXIVComponent().inject(this);
        ButterKnife.bind(this);

        if (BuildConfig.DEBUG) {
            mCharacterNameInput.setText("Cheshire Cat");
            mCharacterServerInput.setText("Phoenix");
        }
    }

    @OnClick(R.id.character_search_button)
    void searchClicked() {
        try {
            Call<XIVDBService.SearchCharactersResponse> charCall = mXIVClient.searchCharacters(mCharacterServerInput.getEditableText().toString(), mCharacterNameInput.getEditableText().toString());
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
