package mochi.task;

/**
 * Represents a task occurring between specified start and end times.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates an incomplete event.
     *
     * @param description description of the event
     * @param from start date or time as entered by the user
     * @param to end date or time as entered by the user
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + super.toFileString() + " | " + from + " | " + to;
    }
}
