package chesire.eorzeaninfo.classes.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassModel implements Parcelable {
    private String name;
    private int level;
    private int id;
    private float level_percent;

    protected ClassModel(Parcel in) {
        name = in.readString();
        level = in.readInt();
        id = in.readInt();
        level_percent = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(level);
        dest.writeInt(id);
        dest.writeFloat(level_percent);
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
