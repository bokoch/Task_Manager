package ua.sumdu.j2se.bokoch.lab1.view;

import ua.sumdu.j2se.bokoch.lab1.model.TaskModel;

/**
 * Интерфейс для фабрики, создания обьекта вида
 */
public interface TaskViewFactory {
    TaskView createView(TaskModel model);
}
