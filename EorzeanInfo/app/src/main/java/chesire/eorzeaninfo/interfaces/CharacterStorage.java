package chesire.eorzeaninfo.interfaces;

import java.util.ArrayList;
import java.util.List;

import chesire.eorzeaninfo.classes.models.CharacterModel;

/**
 * Used to store and retrieve information about a character from within the Android system
 */
public interface CharacterStorage {
    /**
     * Id for when a character isn't found
     */
    int NO_CHARACTER_ID = 0;

    /**
     * Add a character to the list of known characters in storage
     *
     * @param model Model to add
     */
    void addCharacter(CharacterModel model);

    /**
     * Get the character from the list of known characters in storage
     *
     * @param id The id of the character to pull from storage
     * @return CharacterModel of the found character, or null if not found
     */
    CharacterModel getCharacter(int id);

    /**
     * Sets the currently selected character id, which can then be retrieved with getCurrentCharacter
     *
     * @param id id from a CharacterModel to set as the current character
     */
    void setCurrentCharacter(int id);

    /**
     * Get the id of the current set character
     *
     * @return 0 if character not found, otherwise will return the id of the character
     */
    int getCurrentCharacter();

    /**
     * Get a CharacterModel for every known character
     *
     * @return List of every saved character model
     */
    ArrayList<CharacterModel> getAllCharacters();

    /**
     * Get every CharacterModel id
     *
     * @return List of every saved character model id
     */
    List<Integer> getAllCharacterIds();

    void updateCharacter(int id);
}
