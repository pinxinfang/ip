# Mochi acceptance checks

## Core workflow

1. Start with an empty temporary data file and add a todo, deadline, and event.
2. Verify `list`, `mark`, `unmark`, `delete`, `find`, and `bye` responses.
3. Restart Mochi and verify all task details and statuses are retained.

## Error handling

1. Try blank input, an unknown command, missing task details, invalid dates,
   missing event markers, invalid task numbers, and extra arguments.
2. Verify each error is reported without terminating Mochi or changing tasks.
3. Place malformed data in a temporary file and verify startup reports the
   problem and starts with an empty list.

## Sorting

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
