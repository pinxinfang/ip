/**
 * Represents a task with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    private final String taskType;
    private final String dateDetails;

    /**
     * Creates a task that has not been completed.
     *
     * @param description description of the task
     * @param taskType single-letter task type shown in the task list
     * @param dateDetails formatted date/time details, or an empty string for a todo
     */
    public Task(String description, String taskType, String dateDetails) {
        this.description = description;
        this.isDone = false;
        this.taskType = taskType;
        this.dateDetails = dateDetails;
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

    @Override
    public String toString() {
        return "[" + taskType + "][" + getStatusIcon() + "] " + description + dateDetails;
    }
}
