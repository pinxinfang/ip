# Sort acceptance checks

Use a temporary data directory to avoid changing personal tasks.

1. Add `todo Zebra`, `deadline apple /by 2026-09-12`, and
   `event Apple /from noon /to evening`; mark task 1.
2. Enter `sort`. Expect apple, Apple, Zebra in that order, with Zebra done.
3. Restart and enter `list`. Expect the same order and task details.
4. Enter `delete 3`. Expect Zebra to be removed.
5. Enter `sort date`. Expect an error with no list changes.
6. Sort an empty list. Expect an empty task listing without an error.
7. Sort twice. Expect the second result to match the first.

The JUnit MochiTest suite covers mixed task sorting, case ties, persistence,
renumbering, empty lists, and rejected arguments.
