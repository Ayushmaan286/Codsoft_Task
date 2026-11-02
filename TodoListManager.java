import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * To-Do List Manager
 * A console-based task manager for adding, tracking, and clearing everyday to-dos.
 *
 * Java concepts applied:
 *  - ArrayList / Collections for dynamic list management
 *  - OOP: encapsulated Task class with getters, setters, and state
 *  - for-each, while loops and Iterator for traversing task lists
 *  - Dedicated static methods for CRUD operations
 *  - Scanner-driven numeric menu using switch-case
 */
public class TodoListManager {

    // ---------- Task class (encapsulation) ----------

    static class Task {
        private int id;
        private String description;
        private boolean completed;

        public Task(int id, String description) {
            this.id = id;
            this.description = description;
            this.completed = false; // every new task starts as pending
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        @Override
        public String toString() {
            String indicator = completed ? "[✔] Completed" : "[ ] Pending  ";
            return String.format("#%-3d %s  %s", id, indicator, description);
        }
    }

    // ---------- State ----------

    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static int nextId = 1;

    // ---------- CRUD methods ----------

    static void addTask(Scanner sc) {
        System.out.print("Enter task description: ");
        String description = sc.nextLine().trim();

        if (description.isEmpty()) {
            System.out.println("Task description cannot be empty.");
            return;
        }

        tasks.add(new Task(nextId++, description));
        System.out.println("Task added successfully.");
    }

    static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Your to-do list is empty.");
            return;
        }

        System.out.println("\n---------- Your Tasks ----------");
        for (Task task : tasks) { // for-each loop
            System.out.println(task);
        }
        System.out.println("--------------------------------");

        int done = 0;
        for (Task task : tasks) {
            if (task.isCompleted()) {
                done++;
            }
        }
        System.out.println("Completed: " + done + " / " + tasks.size());
    }

    static Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    static void completeTask(Scanner sc) {
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks to complete.");
            return;
        }

        viewTasks();
        int id = readInt(sc, "Enter the ID of the task to mark as completed: ");
        Task task = findTaskById(id);

        if (task == null) {
            System.out.println("No task found with ID " + id + ".");
        } else if (task.isCompleted()) {
            System.out.println("Task #" + id + " is already completed.");
        } else {
            task.setCompleted(true);
            System.out.println("Task #" + id + " marked as completed. ✔");
        }
    }

    static void deleteTask(Scanner sc) {
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks to delete.");
            return;
        }

        viewTasks();
        int id = readInt(sc, "Enter the ID of the task to delete: ");

        boolean removed = false;
        Iterator<Task> it = tasks.iterator(); // iterator + while loop
        while (it.hasNext()) {
            Task task = it.next();
            if (task.getId() == id) {
                it.remove();
                removed = true;
                break;
            }
        }

        if (removed) {
            System.out.println("Task #" + id + " deleted.");
        } else {
            System.out.println("No task found with ID " + id + ".");
        }
    }

    // ---------- Helpers ----------

    static void showMenu() {
        System.out.println("\n===== To-Do List Manager =====");
        System.out.println("  1. Add a task");
        System.out.println("  2. View all tasks");
        System.out.println("  3. Mark a task as completed");
        System.out.println("  4. Delete a task");
        System.out.println("  5. Exit");
        System.out.println("==============================");
    }

    static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = sc.nextInt();
                sc.nextLine(); // consume the leftover newline
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // discard the bad token
            }
        }
    }

    // ---------- Main program ----------

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the To-Do List Manager!");

        while (running) {
            showMenu();
            int choice = readInt(sc, "Choose an option (1-5): ");

            switch (choice) {
                case 1:
                    addTask(sc);
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    completeTask(sc);
                    break;
                case 4:
                    deleteTask(sc);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-5.");
            }
        }

        System.out.println("\nThanks for using the To-Do List Manager. Goodbye!");
        sc.close();
    }
}