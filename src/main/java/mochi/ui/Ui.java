package mochi.ui;

import java.util.Scanner;

import mochi.task.Task;
import mochi.task.TaskList;

/**
 * Handles console input and output for Mochi.
 */
public class Ui {
    private static final String SEPARATOR = "____________________________________________________________";

    private final Scanner scanner;

    /**
     * Creates a console UI that reads standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
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
        System.out.println(SEPARATOR);
        System.out.println("Hello! I'm Mochi.");
        System.out.println("What can I do for you?");
        System.out.println(SEPARATOR);
    }

    /**
     * Displays the response separator.
     */
    public void showLine() {
        System.out.println(SEPARATOR);
    }

    /**
     * Displays Mochi's farewell.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays all tasks with one-based numbering.
     *
     * @param tasks tasks to display
     */
    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        showTasks(tasks);
    }

    /**
     * Displays tasks matching a find command.
     *
     * @param tasks matching tasks to display
     */
    public void showMatchingTasks(TaskList tasks) {
        System.out.println("Here are the matching tasks in your list:");
        showTasks(tasks);
    }

    private void showTasks(TaskList tasks) {
        int index = 1;
        for (Task task : tasks) {
            System.out.println(index + "." + task);
            index++;
        }
    }

    /**
     * Displays confirmation that a task was marked done.
     *
     * @param task task whose status changed
     */
    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Displays confirmation that a task was marked not done.
     *
     * @param task task whose status changed
     */
    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /**
     * Displays a removed task and the remaining count.
     *
     * @param task removed task
     * @param taskCount number of tasks remaining
     */
    public void showDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        showTaskCount(taskCount);
    }

    /**
     * Displays an added task and the updated count.
     *
     * @param task added task
     * @param taskCount number of tasks after addition
     */
    public void showAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        showTaskCount(taskCount);
    }

    /**
     * Displays a recoverable command or storage error.
     *
     * @param message user-friendly error explanation
     */
    public void showError(String message) {
        System.out.println("Oops! " + message);
    }

    /**
     * Displays a grammatically correct task count.
     *
     * @param taskCount number of tasks
     */
    private void showTaskCount(int taskCount) {
        String taskWord = taskCount == 1 ? "task" : "tasks";
        System.out.println("Now you have " + taskCount + " " + taskWord + " in the list.");
    }
}
