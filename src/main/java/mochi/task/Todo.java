package mochi.task;

/**
 * Represents a task without an associated date or time.
 */
public class Todo extends Task {
    /**
     * Creates an incomplete todo.
     *
     * @param description description of the todo
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileString() {
        return "T | " + super.toFileString();
    }
}
