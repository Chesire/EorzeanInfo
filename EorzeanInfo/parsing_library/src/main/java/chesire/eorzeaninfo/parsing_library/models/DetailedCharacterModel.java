package chesire.eorzeaninfo.parsing_library.models;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailedCharacterModel extends BasicCharacterModel implements Parcelable {
    private int lodestone_id;
    private CharacterDataModel data;

    protected DetailedCharacterModel(Parcel in) {
        lodestone_id = in.readInt();
        data = in.readParcelable(CharacterDataModel.class.getClassLoader());
    }

    @Override
    public String getIcon() {
        String avatarUrl = data.getAvatarUrl();
        if (avatarUrl == null) {
            return super.getIcon();
        } else {
            return data.getAvatarUrl();
        }
    }

    @Override
    public int getId() {
        if (lodestone_id == 0) {
            return super.getId();
        } else {
            return lodestone_id;
        }
    }

    @Override
    public String getName() {
        String charName = data.getName();
        if (charName == null) {
            return super.getName();
        } else {
            return charName;
        }
    }

    @Override
    public String getServer() {
        String charServer = data.getServer();
        if (charServer == null) {
            return super.getServer();
        } else {
            return data.getServer();
        }
    }

    /**
     * Get the data object for this character, which contains more information about it
     *
     * @return Data object with more detailed character information
     */
    public CharacterDataModel getData() {
        return data;
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
