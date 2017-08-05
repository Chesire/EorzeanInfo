package chesire.eorzeaninfo.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import chesire.eorzeaninfo.EorzeanInfoApp;
import chesire.eorzeaninfo.R;
import chesire.eorzeaninfo.interfaces.CharacterStorage;
import chesire.eorzeaninfo.parsing_library.models.DetailedCharacterModel;
import chesire.eorzeaninfo.parsing_library.repositories.MinMountRepository;

public class CharacterDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "CharacterDetailActivity";

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    AppCompatImageView mNavHeaderImage;
    AppCompatTextView mNavTitleText;
    AppCompatTextView mNavBodyText;
    @Inject
    CharacterStorage mCharacterStorage;
    @Inject
    MinMountRepository mMinMountRepository;

    private DetailedCharacterModel mCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);

        ButterKnife.bind(this);
        ((EorzeanInfoApp) getApplication()).getCharacterStorageComponent().inject(this);

        mCharacter = mCharacterStorage.getCharacter(mCharacterStorage.getCurrentCharacter());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        View headerView = mNavigationView.getHeaderView(0);

        mNavHeaderImage = ButterKnife.findById(headerView, R.id.nav_header_image);
        mNavTitleText = ButterKnife.findById(headerView, R.id.nav_header_title_text);
        mNavBodyText = ButterKnife.findById(headerView, R.id.nav_header_body_text);

        loadNavigationHeaderData();
        mNavHeaderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(CharacterDetailsActivity.this, R.color.colorPrimary))
                        .build()
                        .launchUrl(CharacterDetailsActivity.this, Uri.parse(mCharacter.getLodestoneUrl()));
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.character_profile_container, CharacterProfileFragment.newInstance(mCharacter))
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.character_details_profile:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.character_profile_container, CharacterProfileFragment.newInstance(mCharacter))
                        .commit();
                break;

            case R.id.character_details_achievements:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.character_profile_container, CharacterAchievementsFragment.newInstance())
                        .commit();
                break;
            case R.id.character_details_mounts:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.character_profile_container, MinMountFragment.newInstance(mCharacter.getData().getMounts(), mMinMountRepository.getAllMounts()))
                        .commit();
                break;

            case R.id.character_details_minions:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.character_profile_container, MinMountFragment.newInstance(mCharacter.getData().getMinions(), mMinMountRepository.getAllMinions()))
                        .commit();
                break;

            case R.id.character_details_switch_character:
                Intent selectCharacterIntent = new Intent(this, CharacterChangeActivity.class);
                startActivity(selectCharacterIntent);
                break;

            case R.id.character_details_settings:
                break;

            default:
                Log.d(TAG, "Unknown navigation item selected");
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadNavigationHeaderData() {
        Glide.with(this)
                .load(mCharacter.getIcon())
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(mNavHeaderImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(CharacterDetailsActivity.this.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        mNavHeaderImage.setImageDrawable(circularBitmapDrawable);
                    }
                });

        mNavTitleText.setText(mCharacter.getName());
        mNavBodyText.setText(mCharacter.getServer());
    }
}
