package chesire.eorzeaninfo.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.classes.GridSpacingItemDecoration;
import chesire.eorzeaninfo.classes.UiUtils;
import chesire.eorzeaninfo.parsing_library.models.MinMountModel;

public class MinMountFragment extends Fragment {
    private static final String TAG = "MinMountFragment";
    private static final String ACQUIRED_MODELS_TAG = "ACQUIRED_MODELS_TAG";
    private static final String ALL_MODELS_TAG = "ALL_MODELS_TAG";
    private static final int NUM_TABS = 3;

    @BindView(R.id.min_mount_tab_layout)
    TabLayout mTabs;
    @BindView(R.id.min_mount_view_pager)
    ViewPager mPager;

    public static MinMountFragment newInstance(List<Integer> acquiredModels, List<MinMountModel> allModels) {
        MinMountFragment fragment = new MinMountFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(ACQUIRED_MODELS_TAG, new ArrayList<>(acquiredModels));
        args.putParcelableArrayList(ALL_MODELS_TAG, new ArrayList<Parcelable>(allModels));
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ArrayList<Integer> acquiredModels = getArguments().getIntegerArrayList(ACQUIRED_MODELS_TAG);
        ArrayList<MinMountModel> allModels = getArguments().getParcelableArrayList(ALL_MODELS_TAG);
        View v = inflater.inflate(R.layout.fragment_min_mount, container, false);
        ButterKnife.bind(this, v);

        mPager.setAdapter(new MinMountAdapter(getContext(), acquiredModels, allModels));
        mTabs.setupWithViewPager(mPager);

        return v;
    }

    class MinMountAdapter extends PagerAdapter {
        @BindView(R.id.min_mount_card_list)
        RecyclerView mRecycler;

        private Context mContext;
        private LayoutInflater mInflater;

        private List<MinMountModel> mAdapterAllModels;
        private List<MinMountModel> mAdapterAcquiredModels;
        private List<MinMountModel> mAdapterNotAcquiredModels;

        MinMountAdapter(Context context, List<Integer> acquiredModels, List<MinMountModel> allModels) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mAdapterAllModels = allModels;

            loadMinMountLists(acquiredModels, allModels);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewGroup layout = (ViewGroup) mInflater.inflate(R.layout.item_min_mount_tab_view, container, false);
            container.addView(layout);
            ButterKnife.bind(this, layout);

            // Load the current list for the adapter
            switch (position) {
                case 0:
                    mRecycler.setAdapter(new MinMountItemAdapter(mAdapterAcquiredModels));
                    break;

                case 1:
                    mRecycler.setAdapter(new MinMountItemAdapter(mAdapterNotAcquiredModels));
                    break;

                case 2:
                    mRecycler.setAdapter(new MinMountItemAdapter(mAdapterAllModels));
                    break;

                default:
                    Log.wtf(TAG, "Unknown position in instantiateItem MinMountFragment - " + position);
                    break;
            }

            mRecycler.setLayoutManager(new GridLayoutManager(mContext, 2));
            mRecycler.addItemDecoration(new GridSpacingItemDecoration(2, UiUtils.convertDpToPx(4, getResources()), true));

            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return NUM_TABS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return mContext.getString(R.string.min_mounts_acquired);

                case 1:
                    return mContext.getString(R.string.min_mounts_not_acquired);

                case 2:
                    return mContext.getString(R.string.min_mounts_all);

                default:
                    throw new IndexOutOfBoundsException("Unexpected value for MinMountFragment");
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        private void loadMinMountLists(List<Integer> modelIds, List<MinMountModel> allPossibleModels) {
            List<MinMountModel> notAcquiredList = new ArrayList<>();
            List<MinMountModel> acquiredList = new ArrayList<>();
            for (MinMountModel model : allPossibleModels) {
                if (!modelIds.contains(model.getId())) {
                    notAcquiredList.add(model);
                } else if (modelIds.contains(model.getId())) {
                    acquiredList.add(model);
                }
            }

            mAdapterAcquiredModels = acquiredList;
            mAdapterNotAcquiredModels = notAcquiredList;
        }
    }

    class MinMountItemAdapter extends RecyclerView.Adapter<MinMountItemAdapter.MinMountItemViewHolder> {
        private Context mContext;
        private List<MinMountModel> mModels;

        MinMountItemAdapter(List<MinMountModel> models) {
            mModels = models;
        }

        @Override
        public MinMountItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(mContext);

            return new MinMountItemViewHolder(inflater.inflate(R.layout.item_min_mount_cardview, parent, false));
        }

        @Override
        public void onBindViewHolder(MinMountItemViewHolder holder, int position) {
            holder.bindModel(mModels.get(position));
        }

        @Override
        public int getItemCount() {
            if (mModels == null) {
                return 0;
            } else {
                return mModels.size();
            }
        }

        class MinMountItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @BindView(R.id.min_mount_item_image)
            AppCompatImageView mImageView;
            @BindView(R.id.min_mount_item_name)
            AppCompatTextView mName;

            private MinMountModel mModel;

            MinMountItemViewHolder(View itemView) {
                super(itemView);

                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(this);
            }

            void bindModel(MinMountModel model) {
                mModel = model;

                mName.setText(model.getName());
                Glide.with(mContext)
                        .load(mModel.getIcon())
                        .placeholder(R.drawable.ic_account_circle_black)
                        .into(mImageView);
            }

            @Override
            public void onClick(View v) {
                new CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                        .build()
                        .launchUrl(getContext(), Uri.parse(mModel.getUrl()));
            }
        }
    }
}
