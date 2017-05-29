package chesire.eorzeaninfo.parsing_library;

/**
 * Class containing various constants to be used in the library
 */
public abstract class Constants {
    /**
     * Base url for accessing the lodestone with a given character, format should be replaced with characters id
     */
    public static final String LODESTONE_URL = "http://na.finalfantasyxiv.com/lodestone/character/%1$s";

    /**
     * Base url for any urls that are in the models
     */
    public static final String XIV_DB_URL = "https://xivdb.com";

    /**
     * Base url for accessing the url of a minion/mount icon url, format should be replaced with the id.
     * 004000 is the url for the lower quality image it looks like, could potentially use one of the others for higher quality
     */
    public static final String MINMOUNT_ICON_URL = "https://xivdb.com/img/game/004000/%1$s.png";
}
