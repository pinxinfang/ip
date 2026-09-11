# Mochi User Guide

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

## Adding deadlines

// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```

## Feature ABC

// Feature details


## Feature XYZ

// Feature details

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
