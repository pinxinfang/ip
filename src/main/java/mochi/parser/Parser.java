package mochi.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import mochi.command.Command;
import mochi.exception.MochiException;
import mochi.task.Deadline;
import mochi.task.Event;
import mochi.task.Task;
import mochi.task.Todo;

/**
 * Interprets user input and validates command parameters.
 */
public class Parser {
    private static final String DEADLINE_SEPARATOR = "/by";
    private static final String EVENT_FROM_SEPARATOR = "/from";
    private static final String EVENT_TO_SEPARATOR = "/to";

    /**
     * Identifies the command represented by the user's input.
     *
     * @param input complete input entered by the user
     * @return parsed command type
     */
    public static Command parseCommand(String input) {
        return Command.fromInput(input);
    }

    /**
     * Converts a task-creation command into the corresponding task type.
     *
     * @param input complete command entered by the user
     * @param command type of task-creation command
     * @return parsed todo, deadline, or event
     * @throws MochiException if the command or any required field is invalid
     */
    public static Task parseTask(String input, Command command) throws MochiException {
        if (command == Command.TODO) {
            String description = getCommandDetails(input, command);
            if (description.isEmpty()) {
                throw new MochiException("A todo needs a description.");
            }
            return new Todo(description);
        }
        if (command == Command.DEADLINE) {
            int byIndex = input.indexOf(DEADLINE_SEPARATOR);
            if (byIndex < 0) {
                throw new MochiException("A deadline needs '/by' followed by a date or time.");
            }
            String description = input.substring(command.getKeyword().length(), byIndex).trim();
            String by = input.substring(byIndex + DEADLINE_SEPARATOR.length()).trim();
            if (description.isEmpty()) {
                throw new MochiException("A deadline needs a description before '/by'.");
            }
            if (by.isEmpty()) {
                throw new MochiException("A deadline needs a date or time after '/by'.");
            }
            try {
                return new Deadline(description, LocalDate.parse(by));
            } catch (DateTimeParseException e) {
                throw new MochiException("Use yyyy-MM-dd for deadline dates, for example: 2026-08-30.");
            }
        }
        if (command == Command.EVENT) {
            int fromIndex = input.indexOf(EVENT_FROM_SEPARATOR);
            int toIndex = input.indexOf(EVENT_TO_SEPARATOR);
            if (fromIndex < 0 || toIndex < fromIndex) {
                throw new MochiException("An event needs both '/from' and '/to' date or time values.");
            }
            String description = input.substring(command.getKeyword().length(), fromIndex).trim();
            String from = input.substring(fromIndex + EVENT_FROM_SEPARATOR.length(), toIndex).trim();
            String to = input.substring(toIndex + EVENT_TO_SEPARATOR.length()).trim();
            if (description.isEmpty()) {
                throw new MochiException("An event needs a description before '/from'.");
            }
            if (from.isEmpty() || to.isEmpty()) {
                throw new MochiException("An event needs values after both '/from' and '/to'.");
            }
            return new Event(description, from, to);
        }
        throw new MochiException("That command does not create a task.");
    }

    private static String getCommandDetails(String input, Command command) {
        return input.substring(command.getKeyword().length()).trim();
    }

    /**
     * Extracts and validates the task number in a mark, unmark, or delete command.
     *
     * @param input complete command entered by the user
     * @param command command type: mark, unmark, or delete
     * @param taskCount number of tasks currently stored
     * @return zero-based index of the selected task
     * @throws MochiException if the task number is missing, nonnumeric, or outside the list
     */
    public static int parseTaskIndex(String input, Command command, int taskCount) throws MochiException {
        String commandWord = command.getKeyword();
        String taskNumber = input.substring(commandWord.length()).trim();
        int taskIndex;
        try {
            taskIndex = Integer.parseInt(taskNumber) - 1;
        } catch (NumberFormatException e) {
            throw new MochiException(
                    "Tell me which task to " + commandWord + " using a number, for example: " + commandWord + " 2");
        }
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new MochiException("That task number is not in your list.");
        }
        return taskIndex;
    }

    /**
     * Extracts the required search keyword from a find command.
     *
     * @param input complete find command entered by the user
     * @return nonempty search keyword
     * @throws MochiException if no keyword was supplied
     */
    public static String parseFindKeyword(String input) throws MochiException {
        String keyword = input.substring(Command.FIND.getKeyword().length()).trim();
        if (keyword.isEmpty()) {
            throw new MochiException("Tell me what to find, for example: find book");
        }
        return keyword;
    }
}
