package chesire.eorzeaninfo;

import android.app.Application;

import chesire.eorzeaninfo.classes.dagger.DaggerXIVComponent;
import chesire.eorzeaninfo.classes.dagger.XIVComponent;
import chesire.eorzeaninfo.classes.dagger.XIVModule;
import chesire.eorzeaninfo.interfaces.XIVDBService;

public class EorzeanInfoApp extends Application {

    private XIVComponent mXIVComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mXIVComponent = DaggerXIVComponent.builder()
                .xIVModule(new XIVModule(XIVDBService.SERVICE_ENDPOINT))
                .build();
    }

    public XIVComponent getXIVComponent() {
        return mXIVComponent;
    }
}