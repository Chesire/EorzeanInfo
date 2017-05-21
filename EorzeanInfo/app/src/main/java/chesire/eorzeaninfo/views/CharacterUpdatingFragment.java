package chesire.eorzeaninfo.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import chesire.eorzeaninfo.EorzeanInfoApp;
import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.classes.models.BasicCharacterModel;
import chesire.eorzeaninfo.classes.models.DetailedCharacterModel;
import chesire.eorzeaninfo.interfaces.CharacterStorage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterUpdatingFragment extends Fragment {
    private static final String MODEL_TAG = "MODEL_TAG";

    @Inject
    CharacterStorage mCharacterStorage;
    private BasicCharacterModel mModel;

    public static CharacterUpdatingFragment newInstance(BasicCharacterModel model) {
        CharacterUpdatingFragment fragment = new CharacterUpdatingFragment();
        Bundle args = new Bundle();
        args.putParcelable(MODEL_TAG, model);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((EorzeanInfoApp) getActivity().getApplication()).getCharacterStorageComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mModel = getArguments().getParcelable(MODEL_TAG);
        mCharacterStorage.updateCharacter(mModel.getId());
        return inflater.inflate(R.layout.fragment_character_updating, container, false);
    }

    public interface CharacterUpdatingListener {
        void onCharacterUpdated(DetailedCharacterModel model);
    }
}
