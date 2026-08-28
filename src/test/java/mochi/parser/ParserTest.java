package mochi.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import mochi.command.Command;
import mochi.exception.MochiException;
import mochi.task.Deadline;
import mochi.task.Event;
import mochi.task.Task;
import mochi.task.Todo;

class ParserTest {
    @Test
    void parseTask_validCommands_createCorrectTaskTypes() throws MochiException {
        Task todo = Parser.parseTask("todo borrow book", Command.TODO);
        Task deadline = Parser.parseTask("deadline submit report /by 2026-08-30", Command.DEADLINE);
        Task event = Parser.parseTask("event meeting /from 2pm /to 4pm", Command.EVENT);

        assertInstanceOf(Todo.class, todo);
        assertEquals("[T][ ] borrow book", todo.toString());
        assertInstanceOf(Deadline.class, deadline);
        assertEquals("[D][ ] submit report (by: Aug 30 2026)", deadline.toString());
        assertInstanceOf(Event.class, event);
        assertEquals("[E][ ] meeting (from: 2pm to: 4pm)", event.toString());
    }

    @Test
    void parseTask_invalidCommands_throwSpecificErrors() {
        assertThrows(MochiException.class, () -> Parser.parseTask("todo", Command.TODO));
        assertThrows(MochiException.class,
                () -> Parser.parseTask("deadline report /by Friday", Command.DEADLINE));
        assertThrows(MochiException.class,
                () -> Parser.parseTask("event meeting /from 2pm", Command.EVENT));
    }

    @Test
    void parseTaskIndex_validAndInvalidNumbers_behaveCorrectly() throws MochiException {
        assertEquals(1, Parser.parseTaskIndex("mark 2", Command.MARK, 3));
        assertThrows(MochiException.class, () -> Parser.parseTaskIndex("mark", Command.MARK, 3));
        assertThrows(MochiException.class, () -> Parser.parseTaskIndex("mark two", Command.MARK, 3));
        assertThrows(MochiException.class, () -> Parser.parseTaskIndex("mark 4", Command.MARK, 3));
    }
}
