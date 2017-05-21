package chesire.eorzeaninfo.views;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.parsing_library.models.MinMountModel;

public class MinMountFragment extends Fragment {
    private static final String MODELS_TAG = "MODELS_TAG";

    private ArrayList<MinMountModel> mModels;

    public static MinMountFragment newInstance(List<MinMountModel> displayedModels) {
        MinMountFragment fragment = new MinMountFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(MODELS_TAG, new ArrayList<Parcelable>(displayedModels));
        fragment.setArguments(args);
        // will need to later have a way to pull all mounts or minions to compare against whats possible to get

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mModels = getArguments().getParcelableArrayList(MODELS_TAG);
        return inflater.inflate(R.layout.fragment_min_mount, container, false);
    }
}
