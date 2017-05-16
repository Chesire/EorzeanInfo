package chesire.eorzeaninfo.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class CharacterDataModel implements Parcelable {
    private String name;
    private String server;
    private String title;
    private String avatar;

    protected CharacterDataModel(Parcel in) {
        name = in.readString();
        server = in.readString();
        title = in.readString();
        avatar = in.readString();
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

    /**
     * Gets the characters currently displayed title
     *
     * @return Title that the character current displays
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the url for the characters avatar icon
     *
     * @return Url that points straight to the characters avatar icon
     */
    public String getAvatar() {
        return avatar;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(name);
        dest.writeString(server);
        dest.writeString(avatar);
    }

    @Override
    public int describeContents() {
        // auto generated
        return 0;
    }

    public static final Creator<CharacterDataModel> CREATOR = new Creator<CharacterDataModel>() {
        @Override
        public CharacterDataModel createFromParcel(Parcel in) {
            return new CharacterDataModel(in);
        }

        @Override
        public CharacterDataModel[] newArray(int size) {
            return new CharacterDataModel[size];
        }
    };

}
