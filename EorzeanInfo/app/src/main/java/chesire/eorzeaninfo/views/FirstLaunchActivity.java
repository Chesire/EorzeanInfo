package chesire.eorzeaninfo.views;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chesire.eorzeaninfo.R;

public class FirstLaunchActivity extends AppCompatActivity {

    @BindView(R.id.first_launch_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.first_launch_pager_tabs)
    TabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mViewPager.setAdapter(new FirstLaunchAdapter(this));
        mTabs.setupWithViewPager(mViewPager, true);
    }

    @OnClick(R.id.first_launch_skip_button)
    void onSkip() {

    }

    @OnClick(R.id.first_launch_next_button)
    void onNext() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    private class FirstLaunchAdapter extends PagerAdapter {
        private Context mContext;
        private LayoutInflater mInflater;

        FirstLaunchAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            FirstLaunchEnum enumValue = FirstLaunchEnum.values()[position];

            ViewGroup layout = (ViewGroup) mInflater.inflate(enumValue.getLayoutResId(), container, false);
            ((AppCompatTextView) layout.findViewById(R.id.pager_item_title)).setText(enumValue.getTitleResId());
            ((AppCompatTextView) layout.findViewById(R.id.pager_item_body)).setText(enumValue.getBodyResId());
            ((AppCompatImageView) layout.findViewById(R.id.pager_item_image)).setImageResource(enumValue.getImageResId());
            container.addView(layout);

            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return FirstLaunchEnum.values().length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private enum FirstLaunchEnum {
        PAGE1(R.layout.item_first_launch_pager_view, R.string.first_launch_title_1, R.string.first_launch_body_1, R.drawable.ic_menu_camera),
        PAGE2(R.layout.item_first_launch_pager_view, R.string.first_launch_title_2, R.string.first_launch_body_2, R.drawable.ic_menu_gallery),
        PAGE3(R.layout.item_first_launch_pager_view, R.string.first_launch_title_3, R.string.first_launch_body_3, R.drawable.ic_menu_manage);

        private int mLayoutResId;
        private int mTitleResId;
        private int mBodyResId;
        private int mImageResId;

        FirstLaunchEnum(int layoutResId, int titleResId, int bodyResId, int imageResId) {
            mLayoutResId = layoutResId;
            mTitleResId = titleResId;
            mBodyResId = bodyResId;
            mImageResId = imageResId;
        }

        public int getLayoutResId() {
            return mLayoutResId;
        }

        public int getTitleResId() {
            return mTitleResId;
        }

        public int getBodyResId() {
            return mBodyResId;
        }

        public int getImageResId() {
            return mImageResId;
        }
    }
}
