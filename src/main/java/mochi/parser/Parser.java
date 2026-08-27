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
            String description = input.substring(4).trim();
            if (description.isEmpty()) {
                throw new MochiException("A todo needs a description.");
            }
            return new Todo(description);
        }
        if (command == Command.DEADLINE) {
            int byIndex = input.indexOf("/by");
            if (byIndex < 0) {
                throw new MochiException("A deadline needs '/by' followed by a date or time.");
            }
            String description = input.substring(8, byIndex).trim();
            String by = input.substring(byIndex + 3).trim();
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
            int fromIndex = input.indexOf("/from");
            int toIndex = input.indexOf("/to");
            if (fromIndex < 0 || toIndex < fromIndex) {
                throw new MochiException("An event needs both '/from' and '/to' date or time values.");
            }
            String description = input.substring(5, fromIndex).trim();
            String from = input.substring(fromIndex + 5, toIndex).trim();
            String to = input.substring(toIndex + 3).trim();
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
}
