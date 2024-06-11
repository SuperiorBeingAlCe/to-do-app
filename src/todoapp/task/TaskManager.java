package todoapp.task;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
private List<Task> 	tasks;

public TaskManager() {
	tasks = new ArrayList<>();
}

public void addTask(Task task) {
    tasks.add(task);
}

public void updateTask(Task task) {
    for (int i = 0; i < tasks.size(); i++) {
        if (tasks.get(i).getId() == task.getId()) {
            tasks.set(i, task);
            break;
        }
    }
}

public void removeTask(Task task) {
    tasks.remove(task);
}

public List<Task> getAllTasks() {
    return tasks;
}

public void clearAllTasks() {
    tasks.clear();
}

public Task getTaskById(int id) {
    for (Task task : tasks) {
        if (task.getId() == id) {
            return task;
        }
    }
    return null;
}

public java.util.List<Task> getTasks() {
    return tasks;
}


}
