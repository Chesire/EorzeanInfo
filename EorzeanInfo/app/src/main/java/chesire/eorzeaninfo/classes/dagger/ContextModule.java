package chesire.eorzeaninfo.classes.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private Context mContext;

    public ContextModule(Context context) {
        mContext = context.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }
}
