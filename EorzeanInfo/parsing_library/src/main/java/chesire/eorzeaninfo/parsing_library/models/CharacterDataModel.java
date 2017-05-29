package chesire.eorzeaninfo.parsing_library.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Model used to hold all information about a character
 */
public class CharacterDataModel implements Parcelable {
    private String name;
    private String server;
    private String title;
    private String avatar;
    private String portrait;
    private String race;
    private String clan;
    private String nameday;
    private GuardianCityCompanyModel guardian;
    private GuardianCityCompanyModel city;
    private GuardianCityCompanyModel grand_company;
    private List<ClassModel> classjobs;
    private List<Integer> mounts;
    private List<Integer> minions;

    protected CharacterDataModel(Parcel in) {
        name = in.readString();
        server = in.readString();
        title = in.readString();
        avatar = in.readString();
        portrait = in.readString();
        race = in.readString();
        clan = in.readString();
        nameday = in.readString();
        guardian = in.readParcelable(GuardianCityCompanyModel.class.getClassLoader());
        city = in.readParcelable(GuardianCityCompanyModel.class.getClassLoader());
        grand_company = in.readParcelable(GuardianCityCompanyModel.class.getClassLoader());
        in.readList(classjobs, ClassModel.class.getClassLoader());
        in.readList(mounts, Integer.class.getClassLoader());
        in.readList(minions, Integer.class.getClassLoader());
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
     * Gets the characters nameday
     *
     * @return Nameday of the character
     */
    public String getNameday() {
        return nameday;
    }

    /**
     * Get the Guardian object, containing information about the characters Guardian
     *
     * @return The Guardian object
     */
    public GuardianCityCompanyModel getGuardian() {
        return guardian;
    }

    /**
     * Get the City object, containing information about the characters city
     *
     * @return The city object
     */
    public GuardianCityCompanyModel getCity() {
        return city;
    }

    /**
     * Get the Grand Company object, containing information about the characters grand company
     *
     * @return The Grand Company object
     */
    public GuardianCityCompanyModel getGrandCompany() {
        return grand_company;
    }

    /**
     * Gets a list of all character class models
     *
     * @return Complete list of all character class models
     */
    public List<ClassModel> getClasses() {
        return classjobs;
    }

    /**
     * Gets the model for the character class represented by cClass
     *
     * @param cClass Enum for which character class to get
     * @return ClassModel for the represented class
     */
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

    /**
     * Gets a list of all acquired mounts
     *
     * @return List of mount ids that have been acquired
     */
    public List<Integer> getMounts() {
        return mounts;
    }

    /**
     * Gets a list of all acquired minions
     *
     * @return List of minion ids that have been acquired
     */
    public List<Integer> getMinions() {
        return minions;
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
        dest.writeString(nameday);
        dest.writeParcelable(guardian, flags);
        dest.writeParcelable(city, flags);
        dest.writeParcelable(grand_company, flags);
        dest.writeList(classjobs);
        dest.writeList(mounts);
        dest.writeList(minions);
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
