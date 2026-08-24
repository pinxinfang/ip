import java.util.Scanner;

/**
 * Runs the Mochi chatbot.
 */
public class Mochi {
    private static final int MAX_TASKS = 100;
    private static final String SEPARATOR = "____________________________________________________________";

    /**
     * Starts Mochi and processes commands until the user enters {@code bye}.
     *
     * @param args command-line arguments; not used
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

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
            if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            } else if (input.equals("mark") || input.startsWith("mark ")) {
                String taskNumber = input.substring(4).trim();
                if (!taskNumber.matches("\\d+")) {
                    printError("Tell me which task to mark using a number, for example: mark 2");
                } else if (taskNumber.length() > 3) {
                    printError("That task number is not in your list.");
                } else {
                    int taskIndex = Integer.parseInt(taskNumber) - 1;
                    if (taskIndex < 0 || taskIndex >= taskCount) {
                        printError("That task number is not in your list.");
                    } else {
                        tasks[taskIndex].markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + tasks[taskIndex]);
                    }
                }
            } else if (input.equals("unmark") || input.startsWith("unmark ")) {
                String taskNumber = input.substring(6).trim();
                if (!taskNumber.matches("\\d+")) {
                    printError("Tell me which task to unmark using a number, for example: unmark 2");
                } else if (taskNumber.length() > 3) {
                    printError("That task number is not in your list.");
                } else {
                    int taskIndex = Integer.parseInt(taskNumber) - 1;
                    if (taskIndex < 0 || taskIndex >= taskCount) {
                        printError("That task number is not in your list.");
                    } else {
                        tasks[taskIndex].markAsNotDone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + tasks[taskIndex]);
                    }
                }
            } else {
                Task task = null;
                if (input.equals("todo") || input.startsWith("todo ")) {
                    String description = input.substring(4).trim();
                    if (description.isEmpty()) {
                        printError("A todo needs a description.");
                    } else {
                        task = new Todo(description);
                    }
                } else if (input.equals("deadline") || input.startsWith("deadline ")) {
                    int byIndex = input.indexOf("/by");
                    if (byIndex < 0) {
                        printError("A deadline needs '/by' followed by a date or time.");
                    } else {
                        String description = input.substring(8, byIndex).trim();
                        String by = input.substring(byIndex + 3).trim();
                        if (description.isEmpty()) {
                            printError("A deadline needs a description before '/by'.");
                        } else if (by.isEmpty()) {
                            printError("A deadline needs a date or time after '/by'.");
                        } else {
                            task = new Deadline(description, by);
                        }
                    }
                } else if (input.equals("event") || input.startsWith("event ")) {
                    int fromIndex = input.indexOf("/from");
                    int toIndex = input.indexOf("/to");
                    if (fromIndex < 0 || toIndex < fromIndex) {
                        printError("An event needs both '/from' and '/to' date or time values.");
                    } else {
                        String description = input.substring(5, fromIndex).trim();
                        String from = input.substring(fromIndex + 5, toIndex).trim();
                        String to = input.substring(toIndex + 3).trim();
                        if (description.isEmpty()) {
                            printError("An event needs a description before '/from'.");
                        } else if (from.isEmpty() || to.isEmpty()) {
                            printError("An event needs values after both '/from' and '/to'.");
                        } else {
                            task = new Event(description, from, to);
                        }
                    }
                } else {
                    printError("I don't know that command yet. Try todo, deadline, event, list, mark, unmark, or bye.");
                }
                if (task != null) {
                    if (taskCount >= MAX_TASKS) {
                        printError("Your task list is full. I can store up to " + MAX_TASKS + " tasks.");
                    } else {
                        tasks[taskCount] = task;
                        taskCount++;
                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + task);
                        String taskWord = taskCount == 1 ? "task" : "tasks";
                        System.out.println("Now you have " + taskCount + " " + taskWord + " in the list.");
                    }
                }
            }
            System.out.println(SEPARATOR);
        }
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
