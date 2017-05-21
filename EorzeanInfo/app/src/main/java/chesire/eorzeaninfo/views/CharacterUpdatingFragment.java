package chesire.eorzeaninfo.views;

import android.content.Context;
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
import chesire.eorzeaninfo.interfaces.UpdateCharacterCallback;

public class CharacterUpdatingFragment extends Fragment implements UpdateCharacterCallback {
    private static final String MODEL_TAG = "MODEL_TAG";
    private static final int MAX_RETRIES = 3;

    @Inject
    CharacterStorage mCharacterStorage;
    private BasicCharacterModel mModel;
    private CharacterUpdatingListener mListener;
    private int mRetries = 0;

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
        mCharacterStorage.updateCharacter(mModel.getId(), this);
        return inflater.inflate(R.layout.fragment_character_updating, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof CharacterUpdatingListener) {
            mListener = (CharacterUpdatingListener) context;
        } else {
            throw new ClassCastException("Context not an instance of CharacterUpdatingListener");
        }
    }

    @Override
    public void onCharacterUpdate(DetailedCharacterModel model, boolean success) {
        if (success) {
            mListener.onCharacterUpdated(model);
        } else {
            if (mRetries < MAX_RETRIES) {
                mRetries++;
                mCharacterStorage.updateCharacter(mModel.getId(), this);
            } else {
                // need to report a proper error for this state
            }
        }
    }

    interface CharacterUpdatingListener {
        void onCharacterUpdated(DetailedCharacterModel model);
    }
}
