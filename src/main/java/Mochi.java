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
            } else if (input.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(input.substring(5)) - 1;
                tasks[taskIndex].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[taskIndex]);
            } else if (input.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(input.substring(7)) - 1;
                tasks[taskIndex].markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[taskIndex]);
            } else {
                Task task;
                if (input.startsWith("todo ")) {
                    String description = input.substring(5);
                    task = new Todo(description);
                } else if (input.startsWith("deadline ")) {
                    int byIndex = input.indexOf(" /by ");
                    String description = input.substring(9, byIndex);
                    String by = input.substring(byIndex + 5);
                    task = new Deadline(description, by);
                } else if (input.startsWith("event ")) {
                    int fromIndex = input.indexOf(" /from ");
                    int toIndex = input.indexOf(" /to ");
                    String description = input.substring(6, fromIndex);
                    String from = input.substring(fromIndex + 7, toIndex);
                    String to = input.substring(toIndex + 5);
                    task = new Event(description, from, to);
                } else {
                    System.out.println("I don't understand that command yet.");
                    System.out.println(SEPARATOR);
                    continue;
                }
                tasks[taskCount] = task;
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + task);
                String taskWord = taskCount == 1 ? "task" : "tasks";
                System.out.println("Now you have " + taskCount + " " + taskWord + " in the list.");
            }
            System.out.println(SEPARATOR);
        }
    }
}
