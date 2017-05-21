package chesire.eorzeaninfo.views;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.parsing_library.models.MinMountModel;

public class MinMountFragment extends Fragment {
    private static final String MODELS_TAG = "MODELS_TAG";
    private static final int NUM_TABS = 3;

    @BindView(R.id.min_mount_tab_layout)
    TabLayout mTabs;
    @BindView(R.id.min_mount_view_pager)
    ViewPager mPager;

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
        View v = inflater.inflate(R.layout.fragment_min_mount, container, false);
        ButterKnife.bind(this, v);

        mPager.setAdapter(new MinMountAdapter(getContext()));
        mTabs.setupWithViewPager(mPager);

        return v;
    }

    private class MinMountAdapter extends PagerAdapter {
        private Context mContext;
        private LayoutInflater mInflater;

        MinMountAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewGroup layout = (ViewGroup) mInflater.inflate(R.layout.item_min_mount_tab_view, container, false);
            container.addView(layout);

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
    }
}
