package chesire.eorzeaninfo.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.parsing_library.models.DetailedCharacterModel;

public class CharacterProfileFragment extends Fragment {
    private static final String CHARACTER_DATA_TAG = "CHARACTER_DATA_TAG";

    public static CharacterProfileFragment newInstance(DetailedCharacterModel characterModel) {
        CharacterProfileFragment fragment = new CharacterProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(CHARACTER_DATA_TAG, characterModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DetailedCharacterModel mCharacterModel = getArguments().getParcelable(CHARACTER_DATA_TAG);
        View v = inflater.inflate(R.layout.fragment_character_profile, container, false);
        ButterKnife.bind(this, v);

        Glide.with(getContext())
                .load(mCharacterModel.getData().getPortraitUrl())
                .into((AppCompatImageView) ButterKnife.findById(v, R.id.character_profile_image));

        ((AppCompatTextView) ButterKnife.findById(v, R.id.character_profile_name_body)).setText(mCharacterModel.getName());

        return v;
    }
}
