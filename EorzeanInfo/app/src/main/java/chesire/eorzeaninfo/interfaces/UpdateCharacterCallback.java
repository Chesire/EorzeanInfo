package chesire.eorzeaninfo.interfaces;

import chesire.eorzeaninfo.parsing_library.models.DetailedCharacterModel;

/**
 * Callback to be passed into the updateCharacter method of the {@link CharacterStorage}
 */
public interface UpdateCharacterCallback {

    /**
     * Callback to execute when the updateCharacter method call finishes
     *
     * @param model   Will be a populated DetailedCharacterModel if successful, or null if not
     * @param success Boolean indicating success or failure
     */
    void onCharacterUpdate(DetailedCharacterModel model, boolean success);
}
