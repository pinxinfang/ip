package mochi;

import java.nio.file.Path;

import mochi.command.Command;
import mochi.exception.MochiException;
import mochi.parser.Parser;
import mochi.storage.Storage;
import mochi.task.Task;
import mochi.task.TaskList;
import mochi.ui.Ui;

/**
 * Coordinates Mochi's user interface, task list, parser, and storage.
 */
public class Mochi {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates Mochi and loads tasks from the specified data file.
     *
     * @param filePath relative path of the task data file
     */
    public Mochi(Path filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (MochiException e) {
            ui.showError(e.getMessage());
            loadedTasks = new TaskList();
        }
        this.tasks = loadedTasks;
    }

    /**
     * Starts Mochi with its default relative data path.
     *
     * @param args command-line arguments; not used
     */
    public static void main(String[] args) {
        new Mochi(Path.of("data", "mochi.txt")).run();
    }

    /**
     * Reads and executes commands until the user exits or input ends.
     */
    public void run() {
        ui.showWelcome();
        while (ui.hasNextCommand()) {
            String input = ui.readCommand();
            Command command = Parser.parseCommand(input);
            ui.showLine();
            if (command == Command.BYE && input.equals(command.getKeyword())) {
                ui.showGoodbye();
                ui.showLine();
                break;
            }
            try {
                executeCommand(input, command);
            } catch (MochiException e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }
    }

    /**
     * Executes one non-exit command and persists any resulting task changes.
     *
     * @param input complete input entered by the user
     * @param command parsed command type
     * @throws MochiException if the command is invalid or storage fails
     */
    private void executeCommand(String input, Command command) throws MochiException {
        switch (command) {
            case LIST:
                if (!input.equals(command.getKeyword())) {
                    throw new MochiException("The list command does not take extra details.");
                }
                ui.showTaskList(tasks);
                break;
            case MARK:
                int markIndex = Parser.parseTaskIndex(input, command, tasks.size());
                Task markedTask = tasks.get(markIndex);
                markedTask.markAsDone();
                storage.save(tasks);
                ui.showMarked(markedTask);
                break;
            case UNMARK:
                int unmarkIndex = Parser.parseTaskIndex(input, command, tasks.size());
                Task unmarkedTask = tasks.get(unmarkIndex);
                unmarkedTask.markAsNotDone();
                storage.save(tasks);
                ui.showUnmarked(unmarkedTask);
                break;
            case DELETE:
                int deleteIndex = Parser.parseTaskIndex(input, command, tasks.size());
                Task removedTask = tasks.remove(deleteIndex);
                storage.save(tasks);
                ui.showDeleted(removedTask, tasks.size());
                break;
            case FIND:
                String keyword = Parser.parseFindKeyword(input);
                ui.showMatchingTasks(tasks.find(keyword));
                break;
            case TODO:
            case DEADLINE:
            case EVENT:
                Task task = Parser.parseTask(input, command);
                tasks.add(task);
                storage.save(tasks);
                ui.showAdded(task, tasks.size());
                break;
            case UNKNOWN:
                throw new MochiException("I don't know that command yet. Try todo, deadline, event, list, find, "
                        + "mark, unmark, delete, or bye.");
            case BYE:
                throw new MochiException("The bye command does not take extra details.");
            default:
                throw new MochiException("I couldn't process that command.");
        }
    }
}
