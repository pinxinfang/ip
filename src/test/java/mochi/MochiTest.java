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
}
