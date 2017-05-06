package chesire.eorzeaninfo.views;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.interfaces.XIVDBService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharacterSearchActivity extends AppCompatActivity {
    @BindView(R.id.character_search_input)
    TextInputEditText mCharacterNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_search);
        ButterKnife.bind(this);

        mCharacterNameInput.setText("Cheshire Cat");
    }

    @OnClick(R.id.character_search_button)
    void searchClicked() {
        // obviously this should be moved out, and probably made into a singleton with Dagger
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.xivdb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        XIVDBService service = retrofit.create(XIVDBService.class);
        try {
            String searchParam = mCharacterNameInput.getEditableText().toString();
            Call<XIVDBService.SearchCharactersResponse> charCall = service.searchCharacters(searchParam);
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
