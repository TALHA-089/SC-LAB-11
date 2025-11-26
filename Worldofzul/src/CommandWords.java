public class CommandWords {
    private static final String[] validCommands = {
            "go", "quit", "help", "look", "take", "drop", "inventory"
    };
    public boolean isCommand(String aString) {
        for (String validCommand : validCommands) {
            if (validCommand.equals(aString))
                return true;
        }
        return false;
    }
    public void showAll() {
        for(String command : validCommands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}