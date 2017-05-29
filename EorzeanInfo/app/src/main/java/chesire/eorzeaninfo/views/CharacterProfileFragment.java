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
import chesire.eorzeaninfo.parsing_library.models.CharacterDataModel;
import chesire.eorzeaninfo.parsing_library.models.DetailedCharacterModel;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

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

        View v;
        if (getActivity().getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
            v = inflater.inflate(R.layout.fragment_character_profile, container, false);
        } else {
            // Later can change this to work with landscape
            v = inflater.inflate(R.layout.fragment_character_profile, container, false);
        }
        ButterKnife.bind(this, v);

        CharacterDataModel data = mCharacterModel.getData();
        Glide.with(getContext())
                .load(data.getPortraitUrl())
                .into((AppCompatImageView) ButterKnife.findById(v, R.id.character_profile_image));

        ((AppCompatTextView) ButterKnife.findById(v, R.id.character_profile_name_body)).setText(mCharacterModel.getName());
        ((AppCompatTextView) ButterKnife.findById(v, R.id.character_profile_title_body)).setText(data.getTitle());
        ((AppCompatTextView) ButterKnife.findById(v, R.id.character_profile_race_body)).setText(String.format("%1$s - %2$s", data.getRace(), data.getClan()));
        ((AppCompatTextView) ButterKnife.findById(v, R.id.character_profile_nameday_body)).setText(data.getNameday());
        ((AppCompatTextView) ButterKnife.findById(v, R.id.character_profile_guardian_body)).setText(data.getGuardian().getName());
        ((AppCompatTextView) ButterKnife.findById(v, R.id.character_profile_city_body)).setText(data.getCity().getName());
        ((AppCompatTextView) ButterKnife.findById(v, R.id.character_profile_grand_company_body)).setText(data.getGrandCompany().getName());

        return v;
    }
}
