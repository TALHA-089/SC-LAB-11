import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

public class Room {
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;

    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        String result = "You are " + description + ".\n" + getExitString();
        if (hasItems()) {
            result += "\n" + getItemString();
        }
        return result;
    }

    private String getExitString() {
        StringBuilder returnString = new StringBuilder("Exits:");
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString.append(" ").append(exit);
        }
        return returnString.toString();
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Add an item to this room.
     * @param item The item to add
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Remove an item from this room by name.
     * @param itemName The name of the item to remove
     * @return The removed item, or null if not found
     */
    public Item removeItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    /**
     * Get an item from this room by name (without removing it).
     * @param itemName The name of the item
     * @return The item, or null if not found
     */
    public Item getItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Get a string describing the items in this room.
     * @return A description of items, or empty string if none
     */
    public String getItemString() {
        if (items.isEmpty()) {
            return "";
        }
        StringBuilder itemStr = new StringBuilder("Items here:");
        for (Item item : items) {
            itemStr.append("\n  - ").append(item.toString());
        }
        return itemStr.toString();
    }

    /**
     * Check if this room has any items.
     * @return true if room has items, false otherwise
     */
    public boolean hasItems() {
        return !items.isEmpty();
    }
}