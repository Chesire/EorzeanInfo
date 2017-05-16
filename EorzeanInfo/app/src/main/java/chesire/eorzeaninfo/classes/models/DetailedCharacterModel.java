package chesire.eorzeaninfo.classes.models;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailedCharacterModel implements Parcelable {
    private int lodestone_id;
    private CharacterDataModel data;

    protected DetailedCharacterModel(Parcel in) {
        lodestone_id = in.readInt();
        data = in.readParcelable(CharacterDataModel.class.getClassLoader());
    }

    /**
     * Get the id for the specific character
     * This id can be used to get specific information about a character
     *
     * @return id for the character
     */
    public int getId() {
        return lodestone_id;
    }

    @Override
    public int describeContents() {
        // auto generated
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(lodestone_id);
        dest.writeParcelable(data, flags);
    }

    public static final Creator<BasicCharacterModel> CREATOR = new Creator<BasicCharacterModel>() {
        @Override
        public BasicCharacterModel createFromParcel(Parcel in) {
            return new BasicCharacterModel(in);
        }

        @Override
        public BasicCharacterModel[] newArray(int size) {
            return new BasicCharacterModel[size];
        }
    };
}
