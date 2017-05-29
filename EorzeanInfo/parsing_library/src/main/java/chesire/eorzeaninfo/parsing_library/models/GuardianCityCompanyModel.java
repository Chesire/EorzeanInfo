package chesire.eorzeaninfo.parsing_library.models;

import android.os.Parcel;
import android.os.Parcelable;

public class GuardianCityCompanyModel implements Parcelable {
    private String icon;
    private String name;
    private String rank;

    protected GuardianCityCompanyModel(Parcel in) {
        icon = in.readString();
        name = in.readString();
        rank = in.readString();
    }

    /**
     * Gets a url link to the icon used to represent the model
     *
     * @return Url for the model
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Gets the name of the model
     *
     * @return Name of the model
     */
    public String getName() {
        return name;
    }

    /**
     * Get the current rank of the Grand company
     *
     * @return Current rank of the Grand company, or null if this isn't a grand company
     */
    public String getRank() {
        return rank;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icon);
        dest.writeString(name);
        dest.writeString(rank);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GuardianCityCompanyModel> CREATOR = new Creator<GuardianCityCompanyModel>() {
        @Override
        public GuardianCityCompanyModel createFromParcel(Parcel in) {
            return new GuardianCityCompanyModel(in);
        }

        @Override
        public GuardianCityCompanyModel[] newArray(int size) {
            return new GuardianCityCompanyModel[size];
        }
    };
}
