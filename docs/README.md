# Mochi User Guide

Mochi is a friendly desktop task assistant for recording todos, deadlines,
and events. It remembers tasks between launches and gives clear feedback when
a command needs correction.

![Mochi desktop interface](Ui.png)


## Quick start

Run `./gradlew run`, then enter commands in the chat window. Press Enter or
click **Send** to submit a command. **Clear** removes the conversation shown
in the window; it does not delete saved tasks.

## Adding tasks

Enter `todo` followed by a description:

```text
todo read the assigned chapter
Got it. I've added this task:
  [T][ ] read the assigned chapter
```

Use `/by` for a deadline date in `yyyy-MM-dd` format:
`deadline submit report /by 2026-09-18`

Use `/from` and `/to` for an event:
`event project meeting /from Mon 2pm /to 4pm`

## Managing tasks

`list` displays all tasks. `mark 2` and `unmark 2` change task 2's status,
while `delete 2` removes it. `find book` searches descriptions without regard
to letter case. `sort` arranges tasks alphabetically and saves the new order.
Use `bye` to end a command-line session.

## Saving tasks

Mochi stores tasks in `data/mochi.txt`. The file is created automatically and
existing tasks are loaded when Mochi starts. The application reports a
friendly error if saved data is unreadable or malformed.

## Sorting tasks

Enter `sort` to arrange all tasks alphabetically by description, ignoring
letter case. Tasks with matching descriptions keep their relative order.
Task types, dates, and completion status do not affect sorting.

For example, after adding `todo Zebra` and `todo apple`, `sort` displays:

```text
Here are the tasks in your list:
1.[T][ ] apple
2.[T][ ] Zebra
```

The new order is saved and retained after restarting Mochi. Use the new list
numbers for `mark`, `unmark`, and `delete`. Sorting an empty list is valid.
`sort` accepts no arguments: `sort date` reports
`Oops! The sort command does not take extra details.` without changing the list.
The existing saved-task format remains compatible.
