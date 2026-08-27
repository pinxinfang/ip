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

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showWelcome() {
        System.out.println(SEPARATOR);
        System.out.println("Hello! I'm Mochi.");
        System.out.println("What can I do for you?");
        System.out.println(SEPARATOR);
    }

    public void showLine() {
        System.out.println(SEPARATOR);
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        int index = 1;
        for (Task task : tasks) {
            System.out.println(index + "." + task);
            index++;
        }
    }

    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void showDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        showTaskCount(taskCount);
    }

    public void showAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        showTaskCount(taskCount);
    }

    public void showError(String message) {
        System.out.println("Oops! " + message);
    }

    private void showTaskCount(int taskCount) {
        String taskWord = taskCount == 1 ? "task" : "tasks";
        System.out.println("Now you have " + taskCount + " " + taskWord + " in the list.");
    }
}
