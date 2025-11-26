# World of Zuul - Text Adventure Game

## Overview
World of Zuul is a simple text-based adventure game implemented in Java. The game demonstrates object-oriented programming principles including **high cohesion**, **loose coupling**, and **responsibility-driven design**.

## Project Structure

```
Worldofzul/
└── src/
    ├── Main.java           # Entry point
    ├── Game.java           # Game orchestration and logic
    ├── Parser.java         # User input parsing
    ├── Command.java        # Command data encapsulation
    ├── CommandWords.java   # Valid command vocabulary
    └── Room.java           # Room state and navigation
```

## Class Documentation

### 1. **Main.java**
- **Responsibility**: Application entry point
- **Key Method**: `main()` - Creates and starts the game
- **Dependencies**: `Game`

### 2. **Game.java**
- **Responsibility**: Game orchestration, command processing, and game flow control
- **Key Methods**:
  - `play()` - Main game loop
  - `processCommand(Command)` - Handles command execution
  - `createRooms()` - Initializes the game world
  - `printWelcome()` - Displays welcome message
  - `printHelp()` - Shows available commands
  - `goRoom(Command)` - Handles room navigation
  - `quit(Command)` - Handles game termination
- **Dependencies**: `Parser`, `Room`, `Command`
- **Design Notes**: Acts as the central coordinator but maintains loose coupling by delegating responsibilities

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
- **Valid Commands**: `go`, `quit`, `help`
- **Dependencies**: None
- **Design Notes**: Centralized command vocabulary management

### 6. **Room.java**
- **Responsibility**: Manage room state, description, and exits
- **Key Methods**:
  - `setExit(String, Room)` - Define exits to other rooms
  - `getExit(String)` - Get room in a given direction
  - `getShortDescription()` - Returns room description
  - `getLongDescription()` - Returns description with exits
  - `getExitString()` - Formats exit information
- **Dependencies**: None (uses `HashMap` and `Set` from Java collections)
- **Design Notes**: Completely independent, highly reusable

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
| `quit` | `quit` | Exit the game |

### Valid Directions:
- `north`, `south`, `east`, `west` (depending on room exits)

## Game Map

```
         [Pub] ----west---- [Outside] ----east---- [Theater]
                                |
                              south
                                |
                              [Lab] ----east---- [Office]
```

## Design Principles

### High Cohesion ✓
Each class has a single, well-defined responsibility:
- `Room` only manages room state
- `Parser` only handles input parsing
- `CommandWords` only manages vocabulary
- `Command` only encapsulates command data
- `Game` only orchestrates game flow

### Loose Coupling ✓
- Minimal dependencies between classes
- `Room` is completely independent
- Changes to one class don't ripple through others
- Easy to extend or modify individual components

### Responsibility-Driven Design ✓
- Each class is responsible for its own data and behavior
- `Room` handles its own exits instead of `Game` managing them
- `Parser` delegates command validation to `CommandWords`
- `Command` provides its own validation methods

## Error Handling

The game handles various error scenarios:

1. **Invalid Commands**: 
   - Input: `jump`
   - Output: `I don't know what you mean...`

2. **Invalid Directions**:
   - Input: `go north` (when no north exit exists)
   - Output: `There is no door!`

3. **Missing Parameters**:
   - Input: `go`
   - Output: `Go where?`

4. **Extra Parameters**:
   - Input: `quit now`
   - Output: `Quit what?`

## Extending the Game

### Adding a New Room
```java
// In Game.createRooms()
Room newRoom = new Room("description of new room");
existingRoom.setExit("direction", newRoom);
newRoom.setExit("opposite_direction", existingRoom);
```

### Adding a New Command
1. Add command to `CommandWords.validCommands[]`
2. Add handler in `Game.processCommand()`
3. Create private method in `Game` to handle the command logic

Example:
```java
// In CommandWords.java
private static final String[] validCommands = {
    "go", "quit", "help", "look"  // Added "look"
};

// In Game.java
else if (commandWord.equals("look")) {
    look(command);
}

private void look(Command command) {
    System.out.println(currentRoom.getLongDescription());
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
- Add items to rooms
- Implement inventory system
- Add NPCs (non-player characters)
- Save/load game state
- Add more complex navigation (up/down)
- Implement a scoring system

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
