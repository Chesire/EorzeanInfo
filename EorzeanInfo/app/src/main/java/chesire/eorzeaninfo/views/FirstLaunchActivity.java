package chesire.eorzeaninfo.views;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import butterknife.OnClick;
import chesire.eorzeaninfo.R;

public class FirstLaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @OnClick(R.id.first_launch_skip_button)
    void onSkip() {

    }

    @OnClick(R.id.first_launch_next_button)
    void onNext() {
        
    }
}
