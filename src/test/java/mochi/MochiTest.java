package mochi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MochiTest {
    @TempDir
    Path tempDir;

    @Test
    void getResponse_commandsAcrossGuiRequests_preserveTaskState() {
        Mochi mochi = Mochi.forGui(tempDir.resolve("data").resolve("mochi.txt"));

        assertTrue(mochi.getResponse("todo read book").contains("[T][ ] read book"));
        assertEquals("Here are the matching tasks in your list:\n1.[T][ ] read book",
                mochi.getResponse("find book"));
        assertEquals("Bye. Hope to see you again soon!", mochi.getResponse("bye"));
    }
    @Test
    void sort_mixedTasks_preservesDetailsAndPersistsNewOrder() {
        Path file = tempDir.resolve("data").resolve("sorted.txt");
        Mochi mochi = Mochi.forGui(file);
        mochi.getResponse("todo Zebra");
        mochi.getResponse("deadline apple /by 2026-09-12");
        mochi.getResponse("event Apple /from noon /to evening");
        mochi.getResponse("mark 1");

        String sorted = mochi.getResponse("sort");
        assertTrue(sorted.indexOf("apple") < sorted.indexOf("Apple"));
        assertTrue(sorted.indexOf("Apple") < sorted.indexOf("Zebra"));
        assertTrue(sorted.contains("3.[T][X] Zebra"));
        assertEquals(sorted, Mochi.forGui(file).getResponse("list"));
        assertTrue(mochi.getResponse("delete 3").contains("[T][X] Zebra"));
    }

    @Test
    void sort_emptyListAndExtraArguments_handleWithoutChangingTasks() {
        Mochi mochi = Mochi.forGui(tempDir.resolve("data").resolve("empty.txt"));
        assertEquals("Here are the tasks in your list:", mochi.getResponse("sort"));
        mochi.getResponse("todo Zebra");
        mochi.getResponse("todo apple");
        String before = mochi.getResponse("list");
        assertEquals("Oops! The sort command does not take extra details.", mochi.getResponse("sort date"));
        assertEquals(before, mochi.getResponse("list"));
    }
}
