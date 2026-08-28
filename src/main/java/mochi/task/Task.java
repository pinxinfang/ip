package mochi.task;

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

    /**
     * Returns the symbol used to display the completion status.
     *
     * @return {@code X} when done, otherwise a space
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void markAsNotDone() {
        isDone = false;
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

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
