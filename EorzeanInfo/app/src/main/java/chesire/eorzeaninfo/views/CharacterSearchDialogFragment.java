package chesire.eorzeaninfo.views;

import android.content.Context;
import android.content.Intent;
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

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import chesire.eorzeaninfo.EorzeanInfoApp;
import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.classes.CharacterModel;
import chesire.eorzeaninfo.interfaces.CharacterStorage;

public class CharacterSearchDialogFragment extends DialogFragment {
    public static String TAG = "CharacterSearchDialogFragment";
    private static String FOUND_CHARACTERS_TAG = "FOUND_CHARACTERS_TAG";

    @BindView(R.id.character_search_dialog_recycler_view)
    RecyclerView mRecyclerView;

    private List<CharacterModel> mCharacters;
    private CharacterSearchAdapter mAdapter;
    @Inject
    CharacterStorage mCharacterStorage;

    public static CharacterSearchDialogFragment newInstance(ArrayList<CharacterModel> foundCharacters) {
        CharacterSearchDialogFragment fragment = new CharacterSearchDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(FOUND_CHARACTERS_TAG, foundCharacters);
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mCharacters = getArguments().getParcelableArrayList(FOUND_CHARACTERS_TAG);
        View v = inflater.inflate(R.layout.fragment_character_search_dialog, container);
        ButterKnife.bind(this, v);

        mAdapter = new CharacterSearchAdapter(mCharacters);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return v;
    }

    class CharacterSearchAdapter extends RecyclerView.Adapter<CharacterSearchAdapter.CharacterSearchViewHolder> {
        private Context mContext;
        private List<CharacterModel> mCharacterModels;

        CharacterSearchAdapter(List<CharacterModel> characterModels) {
            mCharacterModels = characterModels;
        }

        @Override
        public CharacterSearchAdapter.CharacterSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(mContext);

            return new CharacterSearchViewHolder(inflater.inflate(R.layout.item_character_search, parent, false));
        }

        @Override
        public void onBindViewHolder(CharacterSearchViewHolder holder, int position) {
            holder.bindCharacter(mCharacterModels.get(position));
        }

        @Override
        public int getItemCount() {
            return mCharacterModels.size();
        }

        class CharacterSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @BindView(R.id.character_search_dialog_item_image)
            AppCompatImageView mCharacterImage;
            @BindView(R.id.character_search_dialog_item_name)
            AppCompatTextView mCharacterName;
            @BindView(R.id.character_search_dialog_item_server)
            AppCompatTextView mCharacterServer;

            private CharacterModel mCharacter;

            CharacterSearchViewHolder(View itemView) {
                super(itemView);

                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(this);
            }

            void bindCharacter(CharacterModel model) {
                mCharacter = model;

                Glide.with(mContext)
                        .load(mCharacter.getIcon())
                        .into(mCharacterImage);
                mCharacterName.setText(mCharacter.getName());
                mCharacterServer.setText(mCharacter.getServer());
            }

            @Override
            public void onClick(View v) {
                mCharacterStorage.addCharacter(mCharacter);
                Intent loadProfileIntent = new Intent(getContext(), CharacterProfileActivity.class);
                loadProfileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                loadProfileIntent.putExtra(CharacterProfileActivity.SELECTED_CHARACTER_TAG, mCharacter);
                startActivity(loadProfileIntent);
            }
        }
    }
}
