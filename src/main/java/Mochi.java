import java.util.Scanner;

/**
 * Runs the Mochi chatbot.
 */
public class Mochi {
    private static final String SEPARATOR = "____________________________________________________________";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
            System.out.println(input);
            System.out.println(SEPARATOR);
        }
    }
}
