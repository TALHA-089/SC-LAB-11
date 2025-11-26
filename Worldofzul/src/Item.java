/**
 * Class Item - an item in an adventure game.
 * 
 * This class represents items that can be found in rooms,
 * picked up by the player, and used to win the game.
 * 
 * An item has a name, description, and weight.
 * 
 * @author Talha
 * @version 1.0
 */
public class Item {
    private String name;
    private String description;
    private int weight; // in grams

    /**
     * Create an item with a name, description, and weight.
     * @param name The name of the item
     * @param description A description of the item
     * @param weight The weight of the item in grams
     */
    public Item(String name, String description, int weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    /**
     * @return The name of this item
     */
    public String getName() {
        return name;
    }

    /**
     * @return The description of this item
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The weight of this item in grams
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @return A string representation of this item
     */
    @Override
    public String toString() {
        return name + " (" + weight + "g): " + description;
    }
}
