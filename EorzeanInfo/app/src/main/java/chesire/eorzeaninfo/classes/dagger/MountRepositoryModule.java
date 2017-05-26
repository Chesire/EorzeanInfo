package chesire.eorzeaninfo.classes.dagger;

import android.content.Context;

import javax.inject.Singleton;

import chesire.eorzeaninfo.parsing_library.repositories.MountRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class MountRepositoryModule {
    @Provides
    @Singleton
    MountRepository provideMountRepository(Context context) {
        return new MountRepository(context);
    }
}
