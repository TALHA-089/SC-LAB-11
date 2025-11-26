# World of Zuul - Text Adventure Game

## Overview
World of Zuul is an exciting text-based adventure game implemented in Java. Players explore a university campus, collect items, and solve puzzles to win the game. The implementation demonstrates object-oriented programming principles including **high cohesion**, **loose coupling**, and **responsibility-driven design**.

## Game Objective
Find the **ancient golden key** hidden in the basement and bring it to the **computing admin office** to unlock the treasure and win the game!

## Project Structure

```
Worldofzul/
└── src/
    ├── Main.java           # Entry point
    ├── Game.java           # Game orchestration and logic
    ├── Parser.java         # User input parsing
    ├── Command.java        # Command data encapsulation
    ├── CommandWords.java   # Valid command vocabulary
    ├── Room.java           # Room state and navigation
    └── Item.java           # Item representation
```

## Class Documentation

### 1. **Main.java**
- **Responsibility**: Application entry point
- **Key Method**: `main()` - Creates and starts the game
- **Dependencies**: `Game`

### 2. **Game.java**
- **Responsibility**: Game orchestration, command processing, inventory management, and game flow control
- **Key Methods**:
  - `play()` - Main game loop
  - `processCommand(Command)` - Handles command execution
  - `createRooms()` - Initializes the game world and populates items
  - `printWelcome()` - Displays welcome message
  - `printHelp()` - Shows available commands
  - `goRoom(Command)` - Handles room navigation
  - `look()` - Displays current room description
  - `takeItem(Command)` - Picks up items from room
  - `dropItem(Command)` - Drops items from inventory
  - `showInventory()` - Displays player's inventory
  - `getCurrentWeight()` - Calculates total inventory weight
  - `checkWinCondition()` - Checks if player has won
  - `quit(Command)` - Handles game termination
- **Dependencies**: `Parser`, `Room`, `Command`, `Item`
- **Design Notes**: Acts as the central coordinator but maintains loose coupling by delegating responsibilities. Manages player inventory with a weight limit of 5000g.

### 3. **Parser.java**
- **Responsibility**: Parse user input into Command objects
- **Key Methods**:
  - `getCommand()` - Reads and parses user input
  - `showCommands()` - Displays available commands
- **Dependencies**: `Command`, `CommandWords`, `Scanner`
- **Design Notes**: High cohesion - only handles input parsing

### 4. **Command.java**
- **Responsibility**: Encapsulate command data (command word and second word)
- **Key Methods**:
  - `getCommandWord()` - Returns the command word
  - `getSecondWord()` - Returns the second word (parameter)
  - `isUnknown()` - Checks if command is valid
  - `hasSecondWord()` - Checks if command has a parameter
- **Dependencies**: None
- **Design Notes**: Simple data holder with validation methods

### 5. **CommandWords.java**
- **Responsibility**: Define and validate the game's command vocabulary
- **Key Methods**:
  - `isCommand(String)` - Validates if a string is a valid command
  - `showAll()` - Displays all valid commands
- **Valid Commands**: `go`, `quit`, `help`, `look`, `take`, `drop`, `inventory`
- **Dependencies**: None
- **Design Notes**: Centralized command vocabulary management

### 6. **Room.java**
- **Responsibility**: Manage room state, description, exits, and items
- **Key Methods**:
  - `setExit(String, Room)` - Define exits to other rooms
  - `getExit(String)` - Get room in a given direction
  - `getShortDescription()` - Returns room description
  - `getLongDescription()` - Returns description with exits and items
  - `getExitString()` - Formats exit information
  - `addItem(Item)` - Add an item to the room
  - `removeItem(String)` - Remove an item by name
  - `getItem(String)` - Get an item by name
  - `getItemString()` - Get formatted item list
  - `hasItems()` - Check if room contains items
- **Dependencies**: `Item` (uses `HashMap`, `Set`, `ArrayList` from Java collections)
- **Design Notes**: Completely independent, highly reusable. Manages its own items collection.

### 7. **Item.java**
- **Responsibility**: Represent items in the game world
- **Key Methods**:
  - `getName()` - Returns item name
  - `getDescription()` - Returns item description
  - `getWeight()` - Returns item weight in grams
  - `toString()` - Returns formatted item information
- **Dependencies**: None
- **Design Notes**: Simple immutable data class with no behavior beyond getters

## How to Run

### Compile:
```bash
cd /Users/talha/Desktop/SC-LAB/Worldofzul/src
javac *.java
```

### Run:
```bash
java Main
```

## Game Commands

| Command | Usage | Description |
|---------|-------|-------------|
| `help` | `help` | Display available commands and game information |
| `go` | `go <direction>` | Move to an adjacent room (e.g., `go east`) |
| `look` | `look` | Look around and see the current room description again |
| `take` | `take <item>` | Pick up an item from the current room (e.g., `take key`) |
| `drop` | `drop <item>` | Drop an item from your inventory (e.g., `drop torch`) |
| `inventory` | `inventory` | Show all items you're currently carrying with total weight |
| `quit` | `quit` | Exit the game |

### Valid Directions:
- `north`, `south`, `east`, `west`, `up`, `down` (depending on room exits)

### Inventory System:
- Maximum carry weight: **5000 grams**
- Items have different weights
- You cannot pick up items if they would exceed your weight limit
- Use `drop` to make room for heavier items

## Game Map

```
                            [Library]
                                 |
                               north
                                 |
         [Pub] ----west---- [Outside] ----east---- [Theater]
           |                     |                       |
          down                 south                   north
           |                     |                       |
      [Basement]               [Lab] ----east---- [Office]
                                                        
                                                    [Garden]
                                                        |
                                                      north
                                                        |
                                                   [Theater]
```

### Room Descriptions & Items:

| Room | Description | Items |
|------|-------------|-------|
| **Outside** | Main entrance of the university | None |
| **Theater** | Lecture theater | poster (20g) |
| **Pub** | Campus pub | mug (400g), sandwich (200g) |
| **Lab** | Computing lab | laptop (2000g), notebook (150g) |
| **Office** | Computing admin office | None (destination for win) |
| **Library** | University library | book (800g), map (50g) |
| **Garden** | Peaceful garden | flower (10g) |
| **Basement** | Dark basement | **key (100g)** ⭐, torch (300g) |

⭐ The **key** is required to win the game!

## How to Win

### Objective:
1. Navigate through the university campus
2. Find the **basement** (accessible from the pub by going `down`)
3. Pick up the **key** from the basement (`take key`)
4. Navigate to the **computing admin office** (accessible from the lab by going `east`)
5. When you have the key in your inventory and enter the office, you win!

### Strategy Tips:
- Use the `look` command frequently to see items in rooms
- Check your `inventory` to see what you're carrying
- Manage your weight limit carefully (5000g max)
- The `map` in the library can help you navigate
- Explore all rooms to find useful items
- You can `drop` items you don't need to make room for others

### Quick Walkthrough:
```
1. Start at Outside
2. Go west to Pub
3. Go down to Basement
4. Take key
5. Go up to Pub
6. Go east to Outside
7. Go south to Lab
8. Go east to Office
9. WIN!
```

## Design Principles

### High Cohesion ✓
Each class has a single, well-defined responsibility:
- `Room` only manages room state and items
- `Parser` only handles input parsing
- `CommandWords` only manages vocabulary
- `Command` only encapsulates command data
- `Item` only represents item data
- `Game` only orchestrates game flow and player state

### Loose Coupling ✓
- Minimal dependencies between classes
- `Room` is completely independent
- Changes to one class don't ripple through others
- Easy to extend or modify individual components

### Responsibility-Driven Design ✓
- Each class is responsible for its own data and behavior
- `Room` handles its own exits and items instead of `Game` managing them
- `Parser` delegates command validation to `CommandWords`
- `Command` provides its own validation methods
- `Item` encapsulates all item-related data
- `Game` manages player inventory and game state

## Error Handling

The game handles various error scenarios:

1. **Invalid Commands**: 
   - Input: `jump`
   - Output: `I don't know what you mean...`

2. **Invalid Directions**:
   - Input: `go north` (when no north exit exists)
   - Output: `There is no door!`

3. **Missing Parameters**:
   - Input: `go` or `take` or `drop`
   - Output: `Go where?` / `Take what?` / `Drop what?`

4. **Extra Parameters**:
   - Input: `quit now`
   - Output: `Quit what?`

5. **Item Not Found**:
   - Input: `take sword` (when sword is not in room)
   - Output: `That item is not here.`

6. **Item Not in Inventory**:
   - Input: `drop key` (when key is not in inventory)
   - Output: `You don't have that item.`

7. **Weight Limit Exceeded**:
   - Input: `take laptop` (when it would exceed 5000g limit)
   - Output: `That item is too heavy! You can't carry any more.`

## Extending the Game

### Adding a New Room
```java
// In Game.createRooms()
Room newRoom = new Room("description of new room");
existingRoom.setExit("direction", newRoom);
newRoom.setExit("opposite_direction", existingRoom);

// Add items to the new room
newRoom.addItem(new Item("itemName", "description", weightInGrams));
```

### Adding a New Item
```java
// In Game.createRooms() - add item to any room
room.addItem(new Item("itemName", "Item description", weightInGrams));

// Example:
basement.addItem(new Item("lantern", "A glowing lantern", 500));
```

### Adding a New Command
1. Add command to `CommandWords.validCommands[]`
2. Add handler in `Game.processCommand()`
3. Create private method in `Game` to handle the command logic

Example:
```java
// In CommandWords.java
private static final String[] validCommands = {
    "go", "quit", "help", "look", "take", "drop", "inventory", "examine"  // Added "examine"
};

// In Game.java processCommand()
else if (commandWord.equals("examine")) {
    examine(command);
}

// Add handler method
private void examine(Command command) {
    if(!command.hasSecondWord()) {
        System.out.println("Examine what?");
        return;
    }
    String itemName = command.getSecondWord();
    Item item = currentRoom.getItem(itemName);
    if (item != null) {
        System.out.println(item.getDescription());
    } else {
        System.out.println("You don't see that here.");
    }
}
```

### Modifying Win Condition
```java
// In Game.checkWinCondition()
// Current: Have key and be in office
// Modify to add additional requirements:
boolean hasKey = false;
boolean hasTreasureMap = false;

for (Item item : inventory) {
    if (item.getName().equalsIgnoreCase("key")) hasKey = true;
    if (item.getName().equalsIgnoreCase("map")) hasTreasureMap = true;
}

if (hasKey && hasTreasureMap && currentRoom.getShortDescription().contains("office")) {
    // Win message
}
```

## Technical Requirements

- **Java Version**: Java 8 or higher
- **External Dependencies**: None (uses only Java standard library)

## Development Notes

### Code Quality
- No use of magic numbers or strings (except command words centralized in `CommandWords`)
- Consistent naming conventions
- Proper encapsulation with private fields
- Clear separation of concerns

### Future Enhancements
Potential improvements while maintaining design principles:
- ✅ ~~Add items to rooms~~ (Implemented)
- ✅ ~~Implement inventory system~~ (Implemented)
- ✅ ~~Add more complex navigation (up/down)~~ (Implemented)
- Add NPCs (non-player characters) that can give hints or trade items
- Save/load game state to file
- Implement a scoring system based on items collected
- Add time-based challenges or move limits
- Create locked doors that require specific items
- Add combat or puzzle-solving mechanics
- Implement item combinations (e.g., combine items to create new ones)
- Add multiple win/lose conditions
- Create dynamic room descriptions based on game state

## Learning Objectives

This implementation demonstrates:
1. ✅ Object-oriented design principles
2. ✅ Separation of concerns
3. ✅ Encapsulation
4. ✅ Code reusability
5. ✅ Maintainable and extensible architecture
6. ✅ Error handling and validation
7. ✅ Responsibility-driven design (Chapter 6 concepts)

## Authors & Acknowledgments

Based on the "World of Zuul" example from *Objects First with Java* by David J. Barnes and Michael Kölling (BlueJ textbook, Chapter 6).

## License

Educational project for Software Construction lab coursework.