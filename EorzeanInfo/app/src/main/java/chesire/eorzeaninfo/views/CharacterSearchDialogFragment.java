package chesire.eorzeaninfo.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.classes.CharacterModel;

public class CharacterSearchDialogFragment extends DialogFragment {
    public static String TAG = "CharacterSearchDialogFragment";
    private static String FOUND_CHARACTERS_TAG = "FOUND_CHARACTERS_TAG";
    private List<CharacterModel> mCharacters;


    public static CharacterSearchDialogFragment newInstance(ArrayList<CharacterModel> foundCharacters) {
        CharacterSearchDialogFragment fragment = new CharacterSearchDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(FOUND_CHARACTERS_TAG, foundCharacters);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mCharacters = getArguments().getParcelableArrayList(FOUND_CHARACTERS_TAG);
        // need to populate the recyclerView here
        return inflater.inflate(R.layout.fragment_character_search_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
