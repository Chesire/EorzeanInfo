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

/**
 * Repository to hold the data on ALL mounts / minions
 */
public class MinMountRepository {
    private static final String TAG = "MinMountRepository";
    private static final String MOUNTS_FILE = "mounts.json";
    private static final String MINIONS_FILE = "minions.json";

    private List<MinMountModel> mMountModels;
    private List<MinMountModel> mMinionModels;

    public MinMountRepository(Context context) {
        mMountModels = loadCacheFile(context, MOUNTS_FILE);
        mMinionModels = loadCacheFile(context, MINIONS_FILE);

        // I can't think of a nice way to set this data in, so just do it manually for now
        if (mMountModels != null) {
            for (MinMountModel model : mMountModels) {
                model.setType(MinMountModel.MOUNT_MODEL);
            }
        }
        if (mMinionModels != null) {
            for (MinMountModel model : mMinionModels) {
                model.setType(MinMountModel.MINION_MODEL);
            }
        }
    }

    /**
     * Loads a cached file from the system
     *
     * @param context Context to pull the assets
     * @param file    The json file to pull the data for
     */
    private List<MinMountModel> loadCacheFile(Context context, String file) {
        String data = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(file)));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                data += mLine;
            }
        } catch (IOException e) {
            Log.e(TAG, "Error parsing " + file, e);
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
            return new Gson().fromJson(data, new TypeToken<List<MinMountModel>>() {
            }.getType());
        } else {
            Log.wtf(TAG, "Data was not created into a list, returning null");
            return null;
        }
    }

    /**
     * Gets a list of all known mounts
     *
     * @return Full list of all known mount objects
     */
    public List<MinMountModel> getAllMounts() {
        return mMountModels;
    }

    /**
     * Gets a list of all known minions
     *
     * @return Full list of all known minion objects
     */
    public List<MinMountModel> getAllMinions() {
        return mMinionModels;
    }
}
