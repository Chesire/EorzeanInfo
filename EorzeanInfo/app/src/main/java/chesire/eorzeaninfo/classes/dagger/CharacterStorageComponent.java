package chesire.eorzeaninfo.classes.dagger;

import javax.inject.Singleton;

import chesire.eorzeaninfo.LaunchActivity;
import chesire.eorzeaninfo.views.CharacterProfileActivity;
import chesire.eorzeaninfo.views.CharacterSearchDialogFragment;
import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, CharacterStorageModule.class})
public interface CharacterStorageComponent {
    void inject(CharacterProfileActivity activity);

    void inject(LaunchActivity activity);

    void inject(CharacterSearchDialogFragment fragment);
}