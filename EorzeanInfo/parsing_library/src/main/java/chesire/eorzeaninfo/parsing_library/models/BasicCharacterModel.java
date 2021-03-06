package chesire.eorzeaninfo.parsing_library.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model for a singular character object
 */
public class BasicCharacterModel implements Parcelable {
    private String icon;
    private int id;
    private String name;
    private String server;

    /**
     * Package-private constructor used for the {@link DetailedCharacterModel}
     */
    BasicCharacterModel() {
    }

    protected BasicCharacterModel(Parcel in) {
        icon = in.readString();
        id = in.readInt();
        name = in.readString();
        server = in.readString();
    }

    /**
     * Get the url for the characters avatar icon
     *
     * @return Url that points straight to the characters avatar icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Get the id for the specific character
     * This id can be used to get specific information about a character
     *
     * @return id for the character
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of the character
     *
     * @return Name of the character, as "FirstName" "LastName"
     */
    public String getName() {
        return name;
    }

    /**
     * Get the server name the character resides on
     *
     * @return Server that the character resides on
     */
    public String getServer() {
        return server;
    }

    @Override
    public int describeContents() {
        // auto generated
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icon);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(server);
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
