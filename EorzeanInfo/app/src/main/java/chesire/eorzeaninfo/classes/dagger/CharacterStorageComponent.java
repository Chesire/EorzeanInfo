package chesire.eorzeaninfo.classes.dagger;

import javax.inject.Singleton;

import chesire.eorzeaninfo.LaunchActivity;
import chesire.eorzeaninfo.views.CharacterChangeActivity;
import chesire.eorzeaninfo.views.CharacterProfileActivity;
import chesire.eorzeaninfo.views.CharacterSearchActivity;
import chesire.eorzeaninfo.views.CharacterSelectFragment;
import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, CharacterStorageModule.class})
public interface CharacterStorageComponent {
    void inject(CharacterSearchActivity activity);

    void inject(CharacterChangeActivity activity);

    void inject(CharacterProfileActivity activity);

    void inject(LaunchActivity activity);

    void inject(CharacterSelectFragment fragment);
}