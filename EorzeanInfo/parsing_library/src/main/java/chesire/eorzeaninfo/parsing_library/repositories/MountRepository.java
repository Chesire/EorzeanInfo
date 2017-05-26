package chesire.eorzeaninfo.parsing_library.repositories;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import chesire.eorzeaninfo.parsing_library.models.MinMountModel;

public class MountRepository {
    private static final String TAG = "MountRepository";
    private static final String MOUNTS_FILE = "mounts.json";

    private List<MinMountModel> mMountModels;

    public MountRepository(Context context) {
        loadCachedMounts(context);
    }

    /**
     * Load the cached mounts from the default mounts file
     *
     * @param context Context object required to pull the assets
     */
    public void loadCachedMounts(Context context) {
        String data = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(MOUNTS_FILE)));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                data += mLine;
            }
        } catch (IOException e) {
            Log.e(TAG, "Error parsing " + MOUNTS_FILE, e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error closing readerStream", e);
                }
            }
        }

        if (!data.equalsIgnoreCase("")) {
            mMountModels = new Gson().fromJson(data, new TypeToken<List<MinMountModel>>() {
            }.getType());
        }
    }

    public List<MinMountModel> getAllMounts() {
        return mMountModels;
    }
}
