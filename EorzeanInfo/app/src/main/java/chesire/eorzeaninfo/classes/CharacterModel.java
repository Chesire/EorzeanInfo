package chesire.eorzeaninfo.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model for a singular character object
 */
public class CharacterModel implements Parcelable {
    private String icon;
    private int id;
    private String name;
    private String server;
    private String title;

    protected CharacterModel(Parcel in) {
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
