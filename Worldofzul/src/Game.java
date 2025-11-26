import java.util.ArrayList;

public class Game {
    private Parser parser;
    private Room currentRoom;
    private ArrayList<Item> inventory;
    private static final int MAX_CARRY_WEIGHT = 5000; // grams

    public Game() {
        createRooms();
        parser = new Parser();
        inventory = new ArrayList<>();
    }

    private void createRooms() {
        Room outside, theater, pub, lab, office, library, garden, basement;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        library = new Room("in the university library");
        garden = new Room("in a peaceful garden");
        basement = new Room("in a dark basement");

        // initialise room exits using the high cohesion setExit method
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", library);

        theater.setExit("west", outside);
        theater.setExit("north", garden);

        pub.setExit("east", outside);
        pub.setExit("down", basement);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        library.setExit("south", outside);

        garden.setExit("south", theater);

        basement.setExit("up", pub);

        // Add items to rooms
        // The key (win condition item) is in the basement
        basement.addItem(new Item("key", "An ancient golden key with mysterious engravings", 100));
        basement.addItem(new Item("torch", "A flickering torch that provides light", 300));

        // Library items
        library.addItem(new Item("book", "A dusty old book about university history", 800));
        library.addItem(new Item("map", "A map of the university campus", 50));

        // Theater items
        theater.addItem(new Item("poster", "A poster advertising the next play", 20));

        // Pub items  
        pub.addItem(new Item("mug", "An empty beer mug", 400));
        pub.addItem(new Item("sandwich", "A half-eaten sandwich", 200));

        // Lab items
        lab.addItem(new Item("laptop", "A powerful computing laptop", 2000));
        lab.addItem(new Item("notebook", "A notebook full of code snippets", 150));

        // Garden items
        garden.addItem(new Item("flower", "A beautiful red flower", 10));

        // Office is empty but is the destination

        currentRoom = outside;  // start game outside
    }

    public void play() {
        printWelcome();
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is an exciting adventure game.");
        System.out.println("Your goal: Find the ancient key and unlock the treasure in the office!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("take")) {
            takeItem(command);
        }
        else if (commandWord.equals("drop")) {
            dropItem(command);
        }
        else if (commandWord.equals("inventory")) {
            showInventory();
        }
        else {
            // This should never happen if CommandWords and processCommand are in sync
            System.out.println("Command recognized but not implemented!");
        }

        return wantToQuit;
    }

    private void printHelp() {
        System.out.println("You are exploring the university.");
        System.out.println("Your goal is to find the ancient key and unlock the treasure!");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
        System.out.println();
        System.out.println("Commands:");
        System.out.println("  go <direction>  - Move in a direction (north, south, east, west)");
        System.out.println("  look            - Look around the current room");
        System.out.println("  take <item>     - Pick up an item");
        System.out.println("  drop <item>     - Drop an item from inventory");
        System.out.println("  inventory       - Show what you're carrying");
        System.out.println("  help            - Show this help message");
        System.out.println("  quit            - Exit the game");
    }
    private void goRoom(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            // Check win condition after moving
            checkWinCondition();
        }
    }
    private boolean quit(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Look around the current room.
     */
    private void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Try to take an item from the current room.
     */
    private void takeItem(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item item = currentRoom.getItem(itemName);

        if (item == null) {
            System.out.println("That item is not here.");
            return;
        }

        // Check weight limit
        int currentWeight = getCurrentWeight();
        if (currentWeight + item.getWeight() > MAX_CARRY_WEIGHT) {
            System.out.println("That item is too heavy! You can't carry any more.");
            System.out.println("Current weight: " + currentWeight + "g, Max: " + MAX_CARRY_WEIGHT + "g");
            return;
        }

        // Take the item
        currentRoom.removeItem(itemName);
        inventory.add(item);
        System.out.println("You picked up: " + item.getName());
        
        // Check win condition
        checkWinCondition();
    }

    /**
     * Try to drop an item from inventory into the current room.
     */
    private void dropItem(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item itemToDrop = null;

        // Find item in inventory
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemToDrop = item;
                break;
            }
        }

        if (itemToDrop == null) {
            System.out.println("You don't have that item.");
            return;
        }

        // Drop the item
        inventory.remove(itemToDrop);
        currentRoom.addItem(itemToDrop);
        System.out.println("You dropped: " + itemToDrop.getName());
    }

    /**
     * Show the player's inventory.
     */
    private void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }

        int totalWeight = getCurrentWeight();
        System.out.println("You are carrying:");
        for (Item item : inventory) {
            System.out.println("  - " + item.toString());
        }
        System.out.println("Total weight: " + totalWeight + "g / " + MAX_CARRY_WEIGHT + "g");
    }

    /**
     * Get the current total weight of items in inventory.
     * @return Total weight in grams
     */
    private int getCurrentWeight() {
        int total = 0;
        for (Item item : inventory) {
            total += item.getWeight();
        }
        return total;
    }

    /**
     * Check if the player has won the game.
     */
    private void checkWinCondition() {
        // Win condition: Have the key and be in the office
        boolean hasKey = false;
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase("key")) {
                hasKey = true;
                break;
            }
        }

        if (hasKey && currentRoom.getShortDescription().contains("computing admin office")) {
            System.out.println();
            System.out.println("==========================================");
            System.out.println("  CONGRATULATIONS! YOU WON!");
            System.out.println("  You found the ancient key and unlocked");
            System.out.println("  the treasure in the admin office!");
            System.out.println("==========================================");
            System.out.println();
        }
    }
}