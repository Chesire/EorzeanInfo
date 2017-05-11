package chesire.eorzeaninfo.interfaces;

import chesire.eorzeaninfo.classes.CharacterModel;

public interface CharacterStorage {
    int NO_CHARACTER_ID = 0;

    void addCharacter(CharacterModel model);

    CharacterModel getCharacter(int id);

    void setCurrentCharacter(int id);

    /**
     * Get the id of the current set character
     *
     * @return 0 if character not found, otherwise will return the id of the character
     */
    int getCurrentCharacter();
}
