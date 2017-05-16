package chesire.eorzeaninfo.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chesire.eorzeaninfo.R;

public class CharacterProfileFragment extends Fragment {
    public static CharacterProfileFragment newInstance() {
        return new CharacterProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_character_profile, container, false);
    }
}
