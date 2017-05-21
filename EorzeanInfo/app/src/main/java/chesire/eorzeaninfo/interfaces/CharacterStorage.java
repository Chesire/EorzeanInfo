package chesire.eorzeaninfo.interfaces;

import java.util.ArrayList;
import java.util.List;

import chesire.eorzeaninfo.parsing_library.models.BasicCharacterModel;
import chesire.eorzeaninfo.parsing_library.models.DetailedCharacterModel;

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
    void addCharacter(BasicCharacterModel model);

    /**
     * Get the character from the list of known characters in storage
     *
     * @param id The id of the character to pull from storage
     * @return BasicCharacterModel of the found character, or null if not found
     */
    DetailedCharacterModel getCharacter(int id);

    /**
     * Sets the currently selected character id, which can then be retrieved with getCurrentCharacter
     *
     * @param id id from a BasicCharacterModel to set as the current character
     */
    void setCurrentCharacter(int id);

    /**
     * Get the id of the current set character
     *
     * @return 0 if character not found, otherwise will return the id of the character
     */
    int getCurrentCharacter();

    /**
     * Get a BasicCharacterModel for every known character
     *
     * @return List of every saved character model
     */
    ArrayList<BasicCharacterModel> getAllCharacters();

    /**
     * Get every BasicCharacterModel id
     *
     * @return List of every saved character model id
     */
    List<Integer> getAllCharacterIds();

    /**
     * Updates the character model data
     *
     * @param id       id of the character model to update
     * @param callback Callback to execute when the update character call has finished
     */
    void updateCharacter(int id, UpdateCharacterCallback callback);
}

