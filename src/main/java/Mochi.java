import java.util.ArrayList;
import java.util.Scanner;

/**
 * Runs the Mochi chatbot.
 */
public class Mochi {
    private static final String SEPARATOR = "____________________________________________________________";

    /**
     * Starts Mochi and processes commands until the user enters {@code bye}.
     *
     * @param args command-line arguments; not used
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println(SEPARATOR);
        System.out.println("Hello! I'm Mochi.");
        System.out.println("What can I do for you?");
        System.out.println(SEPARATOR);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            System.out.println(SEPARATOR);
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(SEPARATOR);
                break;
            }
            try {
                if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                } else if (input.equals("mark") || input.startsWith("mark ")) {
                    int taskIndex = parseTaskIndex(input, "mark", tasks.size());
                    tasks.get(taskIndex).markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(taskIndex));
                } else if (input.equals("unmark") || input.startsWith("unmark ")) {
                    int taskIndex = parseTaskIndex(input, "unmark", tasks.size());
                    tasks.get(taskIndex).markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(taskIndex));
                } else if (input.equals("delete") || input.startsWith("delete ")) {
                    int taskIndex = parseTaskIndex(input, "delete", tasks.size());
                    Task removedTask = tasks.remove(taskIndex);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removedTask);
                    String taskWord = tasks.size() == 1 ? "task" : "tasks";
                    System.out.println("Now you have " + tasks.size() + " " + taskWord + " in the list.");
                } else {
                    Task task = parseTask(input);
                    tasks.add(task);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    String taskWord = tasks.size() == 1 ? "task" : "tasks";
                    System.out.println("Now you have " + tasks.size() + " " + taskWord + " in the list.");
                }
            } catch (MochiException e) {
                printError(e.getMessage());
            }
            System.out.println(SEPARATOR);
        }
    }

    /**
     * Converts a task-creation command into the corresponding task type.
     *
     * @param input complete command entered by the user
     * @return parsed todo, deadline, or event
     * @throws MochiException if the command or any required field is invalid
     */
    private static Task parseTask(String input) throws MochiException {
        if (input.equals("todo") || input.startsWith("todo ")) {
            String description = input.substring(4).trim();
            if (description.isEmpty()) {
                throw new MochiException("A todo needs a description.");
            }
            return new Todo(description);
        }
        if (input.equals("deadline") || input.startsWith("deadline ")) {
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
            return new Deadline(description, by);
        }
        if (input.equals("event") || input.startsWith("event ")) {
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
        throw new MochiException(
                "I don't know that command yet. Try todo, deadline, event, list, mark, unmark, delete, or bye.");
    }

    /**
     * Extracts and validates the one-based task number in a mark, unmark, or delete command.
     *
     * @param input complete command entered by the user
     * @param command command word: mark, unmark, or delete
     * @param taskCount number of tasks currently stored
     * @return zero-based index of the selected task
     * @throws MochiException if the task number is missing, nonnumeric, or outside the list
     */
    private static int parseTaskIndex(String input, String command, int taskCount) throws MochiException {
        String taskNumber = input.substring(command.length()).trim();
        int taskIndex;
        try {
            taskIndex = Integer.parseInt(taskNumber) - 1;
        } catch (NumberFormatException e) {
            throw new MochiException(
                    "Tell me which task to " + command + " using a number, for example: " + command + " 2");
        }
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new MochiException("That task number is not in your list.");
        }
        return taskIndex;
    }

    /**
     * Displays a friendly error without terminating Mochi.
     *
     * @param message explanation of the invalid command and how to correct it
     */
    private static void printError(String message) {
        System.out.println("Oops! " + message);
    }
}
