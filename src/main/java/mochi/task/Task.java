package mochi.task;

import java.util.Locale;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task that has not been completed.
     *
     * @param description description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Checks whether this task's description contains a keyword, ignoring letter case.
     *
     * @param keyword text to search for
     * @return true when the description contains the keyword
     */
    public boolean containsKeyword(String keyword) {
        return description.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT));
    }

    /**
     * Returns the completion status in the storage format.
     *
     * @return {@code 1} when done, otherwise {@code 0}
     */
    protected String getStorageStatus() {
        return isDone ? "1" : "0";
    }

    /**
     * Converts this task into a line suitable for persistent storage.
     *
     * @return storage representation of this task
     */
    public String toFileString() {
        return getStorageStatus() + " | " + description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
