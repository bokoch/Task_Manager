package ua.sumdu.j2se.bokoch.lab1.view;

import ua.sumdu.j2se.bokoch.tasks.Task;

import java.awt.event.ActionListener;
import java.text.ParseException;

/**
 * Интерфейс для видов
 */
public interface TaskView {
    String ACTION_SHOW_ADD = "add";
    String ACTION_SHOW_EDIT = "edit";
    String ACTION_SHOW_DESC = "describe";
    String ACTION_SHOW_CALENDAR = "calendar";

    String ACTION_ADD = "add_task";
    String ACTION_EDIT = "edit_task";
    String ACTION_DESC = "desc_task";
    String ACTION_DEL = "delete";
    String ACTION_CLOSE = "close";

    /**
     * Добавить слушателя
     * @param al
     */
    void addActionListener(ActionListener al);

    /**
     * Удалить слушателя
     * @param al
     */
    void removeActionListener(ActionListener al);

    /**
     * Возвратить задачу
     * @throws ParseException
     */
    Task getTask() throws ParseException;

    /**
     * Открыть окно
     */
    void show();

    /**
     * Вывод ошибок
     * @param message
     */
    void showError(String message);

    /**
     * Закрыть окно
     */
    void close();
}
