/**
 * Identifies the commands understood by Mochi.
 */
public enum Command {
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    BYE("bye"),
    UNKNOWN("");

    private final String keyword;

    Command(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    /**
     * Identifies a command using the first word of the user's input.
     *
     * @param input complete input entered by the user
     * @return matching command, or {@link #UNKNOWN} when no command matches
     */
    public static Command fromInput(String input) {
        int firstSpace = input.indexOf(' ');
        String commandWord = firstSpace < 0 ? input : input.substring(0, firstSpace);
        for (Command command : values()) {
            if (command.keyword.equals(commandWord)) {
                return command;
            }
        }
        return UNKNOWN;
    }
}
