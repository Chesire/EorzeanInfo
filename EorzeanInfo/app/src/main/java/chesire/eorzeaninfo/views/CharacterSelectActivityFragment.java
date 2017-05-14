package chesire.eorzeaninfo.views;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chesire.eorzeaninfo.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CharacterSelectActivityFragment extends Fragment {

    public CharacterSelectActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_character_select, container, false);
    }
}
