package chesire.eorzeaninfo.classes.dagger;

import android.content.Context;

import javax.inject.Singleton;

import chesire.eorzeaninfo.parsing_library.repositories.MinMountRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class MinMountRepositoryModule {
    @Provides
    @Singleton
    MinMountRepository provideMountRepository(Context context) {
        return new MinMountRepository(context);
    }
}
