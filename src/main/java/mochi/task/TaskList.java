package mochi.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Owns the collection of tasks and provides task-list operations.
 */
public class TaskList implements Iterable<Task> {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list containing the supplied tasks.
     *
     * @param tasks tasks to place in the list
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

    /**
     * Returns a new task list containing tasks whose descriptions match the keyword.
     *
     * @param keyword text to find, matched without regard to letter case
     * @return matching tasks in their original order
     */
    public TaskList find(String keyword) {
        TaskList matches = new TaskList();
        for (Task task : tasks) {
            if (task.containsKeyword(keyword)) {
                matches.add(task);
            }
        }
        return matches;
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}
