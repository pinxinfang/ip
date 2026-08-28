package mochi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TaskListTest {
    @Test
    void find_mixedCaseKeyword_returnsOnlyMatchingTasksInOrder() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("Read Book"));
        tasks.add(new Todo("return book"));
        tasks.add(new Todo("buy bread"));

        TaskList matches = tasks.find("BOOK");

        assertEquals(2, matches.size());
        assertEquals("[T][ ] Read Book", matches.get(0).toString());
        assertEquals("[T][ ] return book", matches.get(1).toString());
    }
}
