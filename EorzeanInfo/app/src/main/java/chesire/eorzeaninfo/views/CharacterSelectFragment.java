package chesire.eorzeaninfo.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import chesire.eorzeaninfo.EorzeanInfoApp;
import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.classes.CharacterModel;
import chesire.eorzeaninfo.interfaces.CharacterStorage;

public class CharacterSelectFragment extends Fragment {
    private static String CHARACTERS_TAG = "CHARACTERS_TAG";

    @BindView(R.id.character_select_card_list)
    RecyclerView mRecyclerView;

    @Inject
    CharacterStorage mCharacterStorage;

    private CharacterSelectAdapter mAdapter;
    private List<CharacterModel> mCharacters;
    private int mCurrentCharacterId;
    private CharacterSelectListener mListener;

    /**
     * Generates a new instance of {@link CharacterSelectFragment}
     *
     * @param models List of characters pulled from the api
     * @return New instance of the {@link CharacterSelectFragment}
     */
    public static CharacterSelectFragment newInstance(ArrayList<CharacterModel> models) {
        CharacterSelectFragment fragment = new CharacterSelectFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CHARACTERS_TAG, models);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((EorzeanInfoApp) getActivity().getApplication()).getCharacterStorageComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCharacters = getArguments().getParcelableArrayList(CHARACTERS_TAG);
        View v = inflater.inflate(R.layout.fragment_character_select, container, false);
        ButterKnife.bind(this, v);

        mAdapter = new CharacterSelectAdapter(mCharacters);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof CharacterSelectListener) {
            mListener = (CharacterSelectListener) context;
        } else {
            throw new ClassCastException("CharacterSelectFragment - Context was not instance of CharacterSelectListener");
        }
    }

    class CharacterSelectAdapter extends RecyclerView.Adapter<CharacterSelectAdapter.CharacterSelectViewHolder> {
        private Context mContext;
        private List<CharacterModel> mCharacterModels;

        CharacterSelectAdapter(List<CharacterModel> characterModels) {
            mCharacterModels = characterModels;
        }

        @Override
        public CharacterSelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(mContext);

            return new CharacterSelectViewHolder(inflater.inflate(R.layout.item_character_select_cardview, parent, false));
        }

        @Override
        public void onBindViewHolder(CharacterSelectViewHolder holder, int position) {
            holder.bindCharacter(mCharacterModels.get(position));
        }

        @Override
        public int getItemCount() {
            return mCharacterModels.size();
        }

        class CharacterSelectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @BindView(R.id.character_select_item_image)
            AppCompatImageView mCharacterImage;
            @BindView(R.id.character_select_item_name)
            AppCompatTextView mCharacterName;
            @BindView(R.id.character_select_item_server)
            AppCompatTextView mCharacterServer;
            @BindView(R.id.character_select_item_current)
            AppCompatTextView mCurrentCharacterMarker;

            private CharacterModel mCharacter;

            CharacterSelectViewHolder(View itemView) {
                super(itemView);

                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(this);
            }

            void bindCharacter(CharacterModel model) {
                mCharacter = model;

                Glide.with(mContext)
                        .load(mCharacter.getIcon())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(mCharacterImage);
                mCharacterName.setText(mCharacter.getName());
                mCharacterServer.setText(mCharacter.getServer());
                mCurrentCharacterMarker.setVisibility(mCurrentCharacterId == mCharacter.getId() ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onClick(View v) {
                mListener.onCharacterSelected(mCharacter);
            }
        }
    }

    interface CharacterSelectListener {
        void onCharacterSelected(CharacterModel model);
    }
}
