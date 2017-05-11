package chesire.eorzeaninfo.interfaces;

import chesire.eorzeaninfo.classes.CharacterModel;

public interface CharacterStorage {
    void addCharacter(CharacterModel model);

    CharacterModel getCharacter(int id);

    void setCurrentCharacter(CharacterModel model);

    /**
     * Get the id of the current set character
     *
     * @return 0 if character not found, otherwise will return the id of the character
     */
    int getCurrentCharacter();
}
