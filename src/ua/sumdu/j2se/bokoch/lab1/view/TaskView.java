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

    void addActionListener(ActionListener al);
    void removeActionListener(ActionListener al);

    Task getTask() throws ParseException;

    void show();
    void showError(String message);
    void close();
}
