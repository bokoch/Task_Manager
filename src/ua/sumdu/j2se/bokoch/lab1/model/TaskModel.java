package ua.sumdu.j2se.bokoch.lab1.model;

import ua.sumdu.j2se.bokoch.tasks.Task;
import ua.sumdu.j2se.bokoch.tasks.TaskList;

import java.util.Date;
import java.util.Observable;
import java.util.Set;
import java.util.SortedMap;

/**
 * Интерфейс модели
 */
public interface TaskModel {
    void addTask(Task task);
    void removeTask(Task task);
    void changeTask(Task oldTask, Task newTask);
    TaskList getList();

    void setSelTask(Task task);
    Task getSelTask();

    Iterable<Task> incoming(Iterable<Task> tasks, Date from, Date to);
    SortedMap<Date, Set<Task>> getCalendarMap(Date from, Date to);

    Observable observable();
}
