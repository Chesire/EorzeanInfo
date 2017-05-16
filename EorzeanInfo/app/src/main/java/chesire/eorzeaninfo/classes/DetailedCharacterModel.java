package chesire.eorzeaninfo.classes;

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

    public static final Creator<CharacterModel> CREATOR = new Creator<CharacterModel>() {
        @Override
        public CharacterModel createFromParcel(Parcel in) {
            return new CharacterModel(in);
        }

        @Override
        public CharacterModel[] newArray(int size) {
            return new CharacterModel[size];
        }
    };
}
