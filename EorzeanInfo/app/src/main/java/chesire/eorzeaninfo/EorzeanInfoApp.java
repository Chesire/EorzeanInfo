package chesire.eorzeaninfo;

import android.app.Application;

import chesire.eorzeaninfo.classes.dagger.DaggerXIVComponent;
import chesire.eorzeaninfo.classes.dagger.XIVComponent;
import chesire.eorzeaninfo.classes.dagger.XIVModule;

public class EorzeanInfoApp extends Application {

    private XIVComponent mXIVComponent;

    @Override
    public void onCreate() {
        super.onCreate();

       mXIVComponent = DaggerXIVComponent.builder()
               .xIVModule(new XIVModule("https://api.xivdb.com"))
               .build();
    }

    public XIVComponent getXIVComponent() {
        return mXIVComponent;
    }
}