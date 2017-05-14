package chesire.eorzeaninfo.classes.dagger;

import javax.inject.Singleton;

import chesire.eorzeaninfo.views.CharacterSearchFragment;
import dagger.Component;

@Singleton
@Component(modules = {XIVModule.class})
public interface XIVComponent {
    void inject(CharacterSearchFragment activity);
}
