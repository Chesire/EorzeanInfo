package chesire.eorzeaninfo.classes.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CharacterDataModel implements Parcelable {
    private String name;
    private String server;
    private String title;
    private String avatar;
    private String portrait;
    private String race;
    private String clan;
    private String gender;
    private String nameday;
    //private Guardian
    //private City
    //private Grand company
    //private Mounts
    //private Minions
    private List<ClassModel> classjobs;

    public ClassModel getCharacterClass(CharacterClasses cClass) {
        ClassModel foundModel = null;
        for (ClassModel model : classjobs) {
            if (model.getId() == cClass.getId()) {
                foundModel = model;
                break;
            }
        }

        return foundModel;
    }

    protected CharacterDataModel(Parcel in) {
        name = in.readString();
        server = in.readString();
        title = in.readString();
        avatar = in.readString();
        portrait = in.readString();
        race = in.readString();
        clan = in.readString();
        gender = in.readString();
        nameday = in.readString();
        in.readList(classjobs, ClassModel.class.getClassLoader());
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
    public String getAvatarUrl() {
        return avatar;
    }

    /**
     * Get the url for the characters portrait image
     *
     * @return Url that points straight to the characters portrait image
     */
    public String getPortraitUrl() {
        return portrait;
    }

    /**
     * Gets the characters race
     *
     * @return Race of the character
     */
    public String getRace() {
        return race;
    }

    /**
     * Gets the characters clan
     *
     * @return Clan of the character
     */
    public String getClan() {
        return clan;
    }

    /**
     * Gets the characters gender
     *
     * @return Gender of the character
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the characters nameday
     *
     * @return Nameday of the character
     */
    public String getNameday() {
        return nameday;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(name);
        dest.writeString(server);
        dest.writeString(avatar);
        dest.writeString(portrait);
        dest.writeString(race);
        dest.writeString(clan);
        dest.writeString(gender);
        dest.writeString(nameday);
        dest.writeList(classjobs);
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
