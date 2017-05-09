package chesire.eorzeaninfo.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.classes.CharacterModel;

public class CharacterSearchDialogFragment extends DialogFragment {
    public static String TAG = "CharacterSearchDialogFragment";
    private static String FOUND_CHARACTERS_TAG = "FOUND_CHARACTERS_TAG";
    private List<CharacterModel> mCharacters;
    @BindView(R.id.character_search_dialog_recycler_view)
    RecyclerView mRecyclerView;

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
        View v = inflater.inflate(R.layout.fragment_character_search_dialog, container);
        ButterKnife.bind(this, v);

        CharacterSearchDialogAdapter adapter = new CharacterSearchDialogAdapter(mCharacters);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return v;
    }

    class CharacterSearchDialogAdapter extends RecyclerView.Adapter {

        @BindView(R.id.character_search_dialog_item_image)
        AppCompatImageView mCharacterImage;
        @BindView(R.id.character_search_dialog_item_name)
        AppCompatTextView mCharacterName;
        @BindView(R.id.character_search_dialog_item_server)
        AppCompatTextView mCharacterServer;

        private List<CharacterModel> mCharacterModels;

        public CharacterSearchDialogAdapter(List<CharacterModel> characterModels) {
            mCharacterModels = characterModels;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View characterView = inflater.inflate(R.layout.item_character_search, parent, false);
            RecyclerView.ViewHolder viewHolder = new CharacterSearchViewHolder(characterView);

            ButterKnife.bind(this, characterView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            CharacterModel character = mCharacterModels.get(position);

            mCharacterName.setText(character.getName());
            mCharacterServer.setText(character.getServer());
        }

        @Override
        public int getItemCount() {
            return mCharacterModels.size();
        }

        public class CharacterSearchViewHolder extends RecyclerView.ViewHolder {
            public CharacterSearchViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
