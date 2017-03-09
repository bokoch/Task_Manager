package ua.sumdu.j2se.bokoch.lab1.view;

import org.apache.log4j.LogManager;
import ua.sumdu.j2se.bokoch.lab1.controller.TaskController;
import ua.sumdu.j2se.bokoch.lab1.model.TaskModel;
import ua.sumdu.j2se.bokoch.tasks.Task;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Абстракктный класс для видов
 */
public abstract class SwingTaskView implements TaskView {
    protected static final org.apache.log4j.Logger viewLogger = LogManager.getLogger(SwingTaskView.class);

    public SwingTaskView(TaskModel model) {
        this.model = model;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createFrame();
            }
        });
    }

    /**
     * Создать окно
     */
    public abstract void createFrame();

    /**
     * Возвращает задачу (для каждого класса по своему)
     * @return Task
     * @throws ParseException
     */
    public abstract Task getTask() throws ParseException;

    /**
     * Добавить слушателя
     * @param al
     */
    @Override
    public void addActionListener(ActionListener al) {
        listeners.add(al);
    }

    /**
     * Удалить слушателя
     * @param al
     */
    @Override
    public void removeActionListener(ActionListener al) {
        listeners.remove(al);
    }

    /**
     * Открыть окно
     */
    @Override
    public void show() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
            }
        });
    }

    /**
     * Закрыть окно
     */
    @Override
    public void close() {
        frame.setVisible(false);
        frame.dispose();
    }

    /**
     * Вывод ошибок
     * @param message
     */
    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Передать команду слушателю
     * @param command
     */
    protected void fireAction(String command) {
        ActionEvent event = new ActionEvent(this,0, command);
        for(Iterator listener = listeners.iterator();
            listener.hasNext();) {
            ((ActionListener) listener.next()).actionPerformed(event);
        }
    }

    protected TaskModel model;
    protected JFrame frame;
    protected ArrayList listeners = new ArrayList();
}
