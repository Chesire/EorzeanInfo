package chesire.eorzeaninfo.parsing_library.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

import chesire.eorzeaninfo.parsing_library.Constants;

/**
 * Model representing a minion or a mount object
 */
public class MinMountModel implements Parcelable {
    public static final String MOUNT_MODEL = "mount";
    public static final String MINION_MODEL = "minion";

    private int id;
    private String type;
    private String name;
    private String icon;
    private String url;

    private MinMountModel(Parcel in) {
        id = in.readInt();
        type = in.readString();
        name = in.readString();
        icon = in.readString();
        url = in.readString();
    }

    /**
     * Returns this objects id
     *
     * @return id of the minion/mount
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the "type" of this object, use either MOUNT_MODEL or MINION_MODEL
     *
     * @param newType MOUNT_MODEL or MINION_MODEL
     */
    public void setType(String newType) {
        type = newType;
    }

    /**
     * Returns this objects name
     *
     * @return Name of the minion/mount
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the url to this objects icon
     *
     * @return URL end for the icon
     */
    public String getIcon() {
        if (icon.length() >= 6) {
            return Constants.XIV_DB_URL + icon;
        } else {
            /* Attempt to construct a URL to use
             * Rules for doing this are available at
             * https://github.com/viion/XIV-Datamining/blob/master/research/icon_paths.md
             */
            StringBuilder builder = new StringBuilder(icon);
            while (builder.length() < 6) {
                builder.insert(0, 0);
            }
            return String.format(Locale.ROOT, Constants.MINMOUNT_ICON_URL, builder.toString());
        }
    }

    /**
     * Returns the url to this object data
     *
     * @return URL end for the data
     */
    public String getUrl() {
        if (url != null) {
            return Constants.XIV_DB_URL + url;
        } else {
            return String.format(Locale.ROOT, "%1$s/%2$s/%3$s/%4$s", Constants.XIV_DB_URL, type, getId(), getName().replace(" ", "+"));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MinMountModel)) {
            return false;
        }
        MinMountModel otherModel = (MinMountModel) obj;

        return otherModel.getId() == getId();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(icon);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MinMountModel> CREATOR = new Creator<MinMountModel>() {
        @Override
        public MinMountModel createFromParcel(Parcel in) {
            return new MinMountModel(in);
        }

        @Override
        public MinMountModel[] newArray(int size) {
            return new MinMountModel[size];
        }
    };
}
