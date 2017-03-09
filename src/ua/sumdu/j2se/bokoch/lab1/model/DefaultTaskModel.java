package ua.sumdu.j2se.bokoch.lab1.model;

import ua.sumdu.j2se.bokoch.tasks.Task;
import ua.sumdu.j2se.bokoch.tasks.TaskList;
import ua.sumdu.j2se.bokoch.tasks.Tasks;

import java.util.Date;
import java.util.Observable;
import java.util.Set;
import java.util.SortedMap;

/**
 * Реализация модели
 * Используется шаблон Observer для уведомления видов об изменении модели
 */
public class DefaultTaskModel extends Observable implements TaskModel {

    public DefaultTaskModel(TaskList tList) {
         this.tList = tList;
    }

    @Override
    public void addTask(Task task) {
        tList.add(task);
        setChanged();
        notifyObservers();
    }

    @Override
    public void removeTask(Task task) {
        tList.remove(task);
        setChanged();
        notifyObservers();
    }

    @Override
    public void changeTask(Task oldTask, Task newTask) {
        for (Task task: tList) {
            if (task.equals(oldTask)) {
                task.setActive(newTask.isActive());
                task.setTitle(newTask.getTitle());
                if (newTask.isRepeated())
                    task.setTime(newTask.getStartTime(), newTask.getEndTime(), newTask.getRepeatInterval());
                else
                    task.setTime(newTask.getTime());
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Задать выбранную из списка задачу
     */
    @Override
    public void setSelTask(Task task) {
        this.selTask = task;
    }

    /**
     * Возвратить выбранную задачу
     */
    @Override
    public Task getSelTask() {
        return selTask;
    }


    @Override
    public TaskList getList() {
        return tList;
    }

    @Override
    public Iterable<Task> incoming(Iterable<Task> tasks, Date from, Date to) {
        return Tasks.incoming(tasks, from, to);
    }

    /**
     * Возвращает календарь задач за указанный промежуток времени
     */
    @Override
    public SortedMap<Date, Set<Task>> getCalendarMap(Date from, Date to) {
        return Tasks.calendar(tList, from, to);
    }

    @Override
    public Observable observable() {
        return this;
    }

    private TaskList tList;
    private Task selTask;
}
