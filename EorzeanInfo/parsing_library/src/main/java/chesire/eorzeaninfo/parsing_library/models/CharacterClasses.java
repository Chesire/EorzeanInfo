package chesire.eorzeaninfo.parsing_library.models;

/**
 * Enumeration of all possible character classes
 */
public enum CharacterClasses {
    // WAR
    Gladiator(1),
    Pugilist(2),
    Marauder(3),
    Lancer(4),
    Archer(5),
    Rogue(29),
    Conjurer(6),
    Thaumaturge(7),
    Arcanist(26),
    Dark_Knight(32),
    Machinist(31),
    Astrologian(33),
    Red_Mage(34),
    Samurai(35),

    // HAND
    Carpenter(8),
    Blacksmith(9),
    Armorer(10),
    Goldsmith(11),
    Leatherworker(12),
    Weaver(13),
    Alchemist(14),
    Culinarian(15),

    // LAND
    Miner(16),
    Botanist(17),
    Fisher(18);

    private int mId;

    /**
     * Since the ID is fixed, we can "always assume" it will be correct
     *
     * @param id
     */
    CharacterClasses(int id) {
        mId = id;
    }

    /**
     * Get the classes id
     *
     * @return id of the character class
     */
    public int getId() {
        return mId;
    }
}