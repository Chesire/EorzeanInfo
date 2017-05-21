package chesire.eorzeaninfo.classes;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Helper class to hold utilities to help with UI
 */
public abstract class UiUtils {

    /**
     * Converting dp to pixel
     */
    public static int convertDpToPx(int dp, Resources res) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics()));
    }
}
