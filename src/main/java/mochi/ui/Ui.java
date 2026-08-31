package mochi.ui;

import java.io.PrintStream;
import java.util.Scanner;

import mochi.task.Task;
import mochi.task.TaskList;

/**
 * Handles console input and output for Mochi.
 */
public class Ui {
    private static final String SEPARATOR = "____________________________________________________________";

    private final Scanner scanner;
    private final PrintStream output;

    /**
     * Creates a console UI that reads standard input.
     */
    public Ui() {
        this(new Scanner(System.in), System.out);
    }

    /**
     * Creates a UI backed by the supplied input and output streams.
     *
     * @param scanner command source
     * @param output response destination
     */
    public Ui(Scanner scanner, PrintStream output) {
        this.scanner = scanner;
        this.output = output;
    }

    /**
     * Checks whether another complete command is available.
     *
     * @return true when another input line can be read
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Reads the next complete command.
     *
     * @return command entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays Mochi's greeting.
     */
    public void showWelcome() {
        output.println(SEPARATOR);
        output.println("Hello! I'm Mochi.");
        output.println("What can I do for you?");
        output.println(SEPARATOR);
    }

    /**
     * Displays the response separator.
     */
    public void showLine() {
        output.println(SEPARATOR);
    }

    /**
     * Displays Mochi's farewell.
     */
    public void showGoodbye() {
        output.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays all tasks with one-based numbering.
     *
     * @param tasks tasks to display
     */
    public void showTaskList(TaskList tasks) {
        output.println("Here are the tasks in your list:");
        showTasks(tasks);
    }

    /**
     * Displays tasks matching a find command.
     *
     * @param tasks matching tasks to display
     */
    public void showMatchingTasks(TaskList tasks) {
        output.println("Here are the matching tasks in your list:");
        showTasks(tasks);
    }

    private void showTasks(TaskList tasks) {
        int index = 1;
        for (Task task : tasks) {
            output.println(index + "." + task);
            index++;
        }
    }

    /**
     * Displays confirmation that a task was marked done.
     *
     * @param task task whose status changed
     */
    public void showMarked(Task task) {
        output.println("Nice! I've marked this task as done:");
        output.println("  " + task);
    }

    /**
     * Displays confirmation that a task was marked not done.
     *
     * @param task task whose status changed
     */
    public void showUnmarked(Task task) {
        output.println("OK, I've marked this task as not done yet:");
        output.println("  " + task);
    }

    /**
     * Displays a removed task and the remaining count.
     *
     * @param task removed task
     * @param taskCount number of tasks remaining
     */
    public void showDeleted(Task task, int taskCount) {
        output.println("Noted. I've removed this task:");
        output.println("  " + task);
        showTaskCount(taskCount);
    }

    /**
     * Displays an added task and the updated count.
     *
     * @param task added task
     * @param taskCount number of tasks after addition
     */
    public void showAdded(Task task, int taskCount) {
        output.println("Got it. I've added this task:");
        output.println("  " + task);
        showTaskCount(taskCount);
    }

    /**
     * Displays a recoverable command or storage error.
     *
     * @param message user-friendly error explanation
     */
    public void showError(String message) {
        output.println("Oops! " + message);
    }

    /**
     * Displays a grammatically correct task count.
     *
     * @param taskCount number of tasks
     */
    private void showTaskCount(int taskCount) {
        String taskWord = taskCount == 1 ? "task" : "tasks";
        output.println("Now you have " + taskCount + " " + taskWord + " in the list.");
    }
}
