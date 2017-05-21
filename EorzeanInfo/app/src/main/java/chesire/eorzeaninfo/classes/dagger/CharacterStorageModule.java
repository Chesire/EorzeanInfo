package chesire.eorzeaninfo.classes.dagger;

import android.content.Context;

import javax.inject.Singleton;

import chesire.eorzeaninfo.classes.SharedPreferencesCharacterStorage;
import chesire.eorzeaninfo.interfaces.CharacterStorage;
import chesire.eorzeaninfo.interfaces.XIVDBService;
import dagger.Module;
import dagger.Provides;

@Module
public class CharacterStorageModule {
    @Provides
    @Singleton
    CharacterStorage provideCharacterStorage(Context context, XIVDBService xivService) {
        return new SharedPreferencesCharacterStorage(context, xivService);
    }
}
