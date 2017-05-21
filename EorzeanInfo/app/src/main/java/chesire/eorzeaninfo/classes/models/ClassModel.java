package chesire.eorzeaninfo.classes.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model representing a single class/job
 */
public class ClassModel implements Parcelable {
    private String name;
    private int level;
    private int id;
    private float level_percent;
    private String icon;

    protected ClassModel(Parcel in) {
        name = in.readString();
        level = in.readInt();
        id = in.readInt();
        level_percent = in.readFloat();
        icon = in.readString();
    }

    /**
     * Get the classes name
     *
     * @return Name of the class
     */
    public String getName() {
        return name;
    }

    /**
     * Get the current level of the class
     *
     * @return Current level of the class
     */
    public int getLevel() {
        return level;
    }

    /**
     * Get the classes id
     *
     * @return id of the class
     */
    public int getId() {
        return id;
    }

    /**
     * Get the % of what level the class is relative to the max
     *
     * @return % of how close to max level the class is
     */
    public float getLevelPercent() {
        return level_percent;
    }

    /**
     * Get the URL to pull the classes icon
     *
     * @return String representation of the URL to get the class icon
     */
    public String getIconUrl() {
        return icon;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(level);
        dest.writeInt(id);
        dest.writeFloat(level_percent);
        dest.writeString(icon);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClassModel> CREATOR = new Creator<ClassModel>() {
        @Override
        public ClassModel createFromParcel(Parcel in) {
            return new ClassModel(in);
        }

        @Override
        public ClassModel[] newArray(int size) {
            return new ClassModel[size];
        }
    };
}
