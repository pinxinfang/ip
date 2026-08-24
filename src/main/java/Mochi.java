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
        String[] tasks = new String[MAX_TASKS];
        boolean[] isDone = new boolean[MAX_TASKS];
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
                for (int i = 0; i < taskCount; i++) {
                    String status = isDone[i] ? "X" : " ";
                    System.out.println((i + 1) + ".[" + status + "] " + tasks[i]);
                }
            } else if (input.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(input.substring(5)) - 1;
                isDone[taskIndex] = true;
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  [X] " + tasks[taskIndex]);
            } else {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println("added: " + input);
            }
            System.out.println(SEPARATOR);
        }
    }
}
