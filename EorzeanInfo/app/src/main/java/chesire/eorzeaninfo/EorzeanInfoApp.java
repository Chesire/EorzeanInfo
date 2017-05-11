package chesire.eorzeaninfo;

import android.app.Application;

import chesire.eorzeaninfo.classes.dagger.CharacterStorageComponent;
import chesire.eorzeaninfo.classes.dagger.CharacterStorageModule;
import chesire.eorzeaninfo.classes.dagger.ContextModule;
import chesire.eorzeaninfo.classes.dagger.DaggerCharacterStorageComponent;
import chesire.eorzeaninfo.classes.dagger.DaggerXIVComponent;
import chesire.eorzeaninfo.classes.dagger.XIVComponent;
import chesire.eorzeaninfo.classes.dagger.XIVModule;
import chesire.eorzeaninfo.interfaces.XIVDBService;

public class EorzeanInfoApp extends Application {

    private XIVComponent mXIVComponent;
    private CharacterStorageComponent mCharacterStorageComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mXIVComponent = DaggerXIVComponent.builder()
                .xIVModule(new XIVModule(XIVDBService.SERVICE_ENDPOINT))
                .build();

        mCharacterStorageComponent = DaggerCharacterStorageComponent.builder()
                .contextModule(new ContextModule(this))
                .characterStorageModule(new CharacterStorageModule())
                .build();
    }

    public XIVComponent getXIVComponent() {
        return mXIVComponent;
    }

    public CharacterStorageComponent getCharacterStorageComponent() {
        return mCharacterStorageComponent;
    }
}