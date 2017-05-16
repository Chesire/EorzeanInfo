package chesire.eorzeaninfo.classes.dagger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import chesire.eorzeaninfo.classes.models.ClassModel;
import chesire.eorzeaninfo.classes.serializers.ClassModelDeserializer;
import chesire.eorzeaninfo.interfaces.XIVDBService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class XIVModule {
    private String mBaseUrl;

    public XIVModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ClassModel.class, new ClassModelDeserializer())
                .create();

        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    XIVDBService provideXIVDBService(Retrofit retrofit) {
        return retrofit.create(XIVDBService.class);
    }
}
