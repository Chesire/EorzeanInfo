package chesire.eorzeaninfo.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class CharacterModel implements Parcelable {
    private String icon;
    private int id;
    private String last_updated;
    private String name;
    private String server;
    private String url;
    private String url_api;
    private String url_type;
    private String url_xivdb;

    protected CharacterModel(Parcel in) {
        icon = in.readString();
        id = in.readInt();
        last_updated = in.readString();
        name = in.readString();
        server = in.readString();
        url = in.readString();
        url_api = in.readString();
        url_type = in.readString();
        url_xivdb = in.readString();
    }

    public String getIcon() {
        return icon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

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
        dest.writeString(last_updated);
        dest.writeString(name);
        dest.writeString(server);
        dest.writeString(url);
        dest.writeString(url_api);
        dest.writeString(url_type);
        dest.writeString(url_xivdb);
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
