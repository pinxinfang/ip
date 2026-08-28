package mochi.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import mochi.exception.MochiException;
import mochi.task.Deadline;
import mochi.task.Event;
import mochi.task.Task;
import mochi.task.TaskList;
import mochi.task.Todo;

/**
 * Loads and saves Mochi tasks using a local text file.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates storage that uses the given OS-independent path.
     *
     * @param filePath location of the task data file
     */
    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads saved tasks, returning an empty list when the data file does not exist yet.
     *
     * @return tasks restored from disk
     * @throws MochiException if the data file cannot be read or contains invalid data
     */
    public ArrayList<Task> load() throws MochiException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }
        try {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                tasks.add(parseStoredTask(lines.get(i), i + 1));
            }
            return tasks;
        } catch (IOException e) {
            throw new MochiException("I couldn't read your saved tasks: " + e.getMessage());
        }
    }

    /**
     * Saves all tasks, creating the data directory and file when necessary.
     *
     * @param tasks tasks to persist
     * @throws MochiException if the tasks cannot be written
     */
    public void save(TaskList tasks) throws MochiException {
        ArrayList<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            lines.add(task.toFileString());
        }
        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new MochiException("I couldn't save your tasks: " + e.getMessage());
        }
    }

    /**
     * Converts one line of stored data into a task.
     *
     * @param line line read from the data file
     * @param lineNumber one-based line number used for error reporting
     * @return restored task
     * @throws MochiException if the line is malformed
     */
    private Task parseStoredTask(String line, int lineNumber) throws MochiException {
        String[] fields = line.split(" \\| ", -1);
        try {
            Task task;
            switch (fields[0]) {
            case "T":
                task = new Todo(fields[2]);
                break;
            case "D":
                task = new Deadline(fields[2], LocalDate.parse(fields[3]));
                break;
            case "E":
                task = new Event(fields[2], fields[3], fields[4]);
                break;
            default:
                throw new IllegalArgumentException("unknown task type");
            }
            if (fields[1].equals("1")) {
                task.markAsDone();
            } else if (!fields[1].equals("0")) {
                throw new IllegalArgumentException("invalid task status");
            }
            return task;
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException | DateTimeParseException e) {
            throw new MochiException("Saved task data is invalid at line " + lineNumber + ".");
        }
    }
}
