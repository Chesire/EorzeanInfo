package chesire.eorzeaninfo.parsing_library.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model representing a minion or a mount object
 */
public class MinMountModel implements Parcelable {
    private int id;
    private String name;
    private String icon;
    private String url;

    private MinMountModel(Parcel in) {
        id = in.readInt();
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
        return icon;
    }

    /**
     * Returns the url to this object data
     *
     * @return URL end for the data
     */
    public String getUrl() {
        return url;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
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
