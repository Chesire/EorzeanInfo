package chesire.eorzeaninfo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import chesire.eorzeaninfo.classes.dagger.CharacterStorageComponent;
import chesire.eorzeaninfo.classes.dagger.CharacterStorageModule;
import chesire.eorzeaninfo.classes.dagger.ContextModule;
import chesire.eorzeaninfo.classes.dagger.DaggerCharacterStorageComponent;
import chesire.eorzeaninfo.classes.dagger.DaggerXIVComponent;
import chesire.eorzeaninfo.classes.dagger.MinMountRepositoryModule;
import chesire.eorzeaninfo.classes.dagger.XIVComponent;
import chesire.eorzeaninfo.classes.dagger.XIVModule;
import chesire.eorzeaninfo.interfaces.XIVDBService;
import timber.log.Timber;

/**
 * Application override used to setup and retrieve Dagger components
 */
public class EorzeanInfoApp extends Application {

    private XIVComponent mXIVComponent;
    private CharacterStorageComponent mCharacterStorageComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        Timber.plant(new Timber.DebugTree());

        XIVModule xiv = new XIVModule(XIVDBService.SERVICE_ENDPOINT);
        mXIVComponent = DaggerXIVComponent.builder()
                .xIVModule(xiv)
                .build();

        mCharacterStorageComponent = DaggerCharacterStorageComponent.builder()
                .contextModule(new ContextModule(this))
                .characterStorageModule(new CharacterStorageModule())
                .xIVModule(xiv)
                .minMountRepositoryModule(new MinMountRepositoryModule())
                .build();
    }

    /**
     * Setup the XIVComponent
     *
     * @return Initialized XIVComponent
     */
    public XIVComponent getXIVComponent() {
        return mXIVComponent;
    }

    /**
     * Setup the CharacterStorageComponent
     *
     * @return Initialized CharacterStorageComponent
     */
    public CharacterStorageComponent getCharacterStorageComponent() {
        return mCharacterStorageComponent;
    }
}