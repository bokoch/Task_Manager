package ua.sumdu.j2se.bokoch.lab1.view;

import org.apache.log4j.LogManager;
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
     * Каждая реализация вида создает окно по своему
     */
    public abstract void createFrame();

    /**
     * Возвращает задачу (для каждой реализации по своему)
     */
    public abstract Task getTask();

    @Override
    public void addActionListener(ActionListener al) {
        listeners.add(al);
    }

    @Override
    public void removeActionListener(ActionListener al) {
        listeners.remove(al);
    }

    @Override
    public abstract void show();

    @Override
    public void close() {
        frame.setVisible(false);
        frame.dispose();
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Передать команду слушателю
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
