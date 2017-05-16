package chesire.eorzeaninfo.classes.dagger;

import javax.inject.Singleton;

import chesire.eorzeaninfo.LaunchActivity;
import chesire.eorzeaninfo.views.CharacterChangeActivity;
import chesire.eorzeaninfo.views.CharacterDetailsActivity;
import chesire.eorzeaninfo.views.CharacterSelectFragment;
import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, CharacterStorageModule.class, XIVModule.class})
public interface CharacterStorageComponent {
    void inject(LaunchActivity activity);

    void inject(CharacterChangeActivity activity);

    void inject(CharacterDetailsActivity activity);

    void inject(CharacterSelectFragment fragment);
}