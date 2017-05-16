package chesire.eorzeaninfo.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chesire.eorzeaninfo.R;

public class CharacterAchievementsFragment extends Fragment {
    public static CharacterAchievementsFragment newInstance() {
        return new CharacterAchievementsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_character_achievements, container, false);
    }
}
