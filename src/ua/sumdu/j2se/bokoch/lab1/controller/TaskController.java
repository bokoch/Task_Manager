package ua.sumdu.j2se.bokoch.lab1.controller;

import ua.sumdu.j2se.bokoch.lab1.model.DefaultTaskModel;
import ua.sumdu.j2se.bokoch.lab1.model.TaskModel;
import ua.sumdu.j2se.bokoch.lab1.view.*;
import ua.sumdu.j2se.bokoch.tasks.ArrayTaskList;
import ua.sumdu.j2se.bokoch.tasks.TaskIO;
import ua.sumdu.j2se.bokoch.tasks.TaskList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Контроллер.
 * Обрабатывает события и обновляет модель
 */
public class TaskController implements ActionListener {
    String AddSwingTaskName = AddSwingTaskView.class.getName();
    String CalendarSwingTaskName = CalendarSwingView.class.getName();
    String DescribeSwingTaskName = DescribeSwingTask.class.getName();
    String EditSwingTaskName = EditSwingTaskView.class.getName();

    public static void main(String[] args) {
        TaskList arr = new ArrayTaskList();
        try {
            TaskIO.readText(arr, new File("d:\\text1.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TaskController controller = new TaskController(new DefaultTaskModel(arr));
         controller.createView(MainSwingTaskView.getFactory());
        controller.createView(AddSwingTaskView.getFactory());
        controller.createView(EditSwingTaskView.getFactory());
        controller.createView(DescribeSwingTask.getFactory());
        controller.createView(CalendarSwingView.getFactory());
    }

    public TaskController(TaskModel model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        TaskView view = (TaskView) event.getSource();
        if (event.getActionCommand().equals(view.ACTION_CLOSE)) {
            view.close();
            try {
                TaskIO.writeText(model.getList(), new File("d:\\text1.txt"));
            } catch (IOException e) {}
            views.remove(view);
            if (views.size() == 0)
                System.exit(0);
        }
        //Открываем окно добавления задачи
        if (event.getActionCommand().equals(TaskView.ACTION_SHOW_ADD)) {
            viewsMap.get(AddSwingTaskName).show();
        }
        //Сохраняем выбранную задачу и передаем в окно редактирования задачи
        //Открываем окно редактирования задачи
        if (event.getActionCommand().equals(TaskView.ACTION_SHOW_EDIT)) {
            try {
                model.setSelTask(view.getTask());
                viewsMap.get(EditSwingTaskName).show();
            } catch (Exception e) {
                view.showError(e.getMessage());
            }
        }

        //Сохраняем выбранную задачу и передаем в окно описания задачи
        //Открываем окно описания задачи
        if (event.getActionCommand().equals(TaskView.ACTION_SHOW_DESC)) {
            try {
                model.setSelTask(view.getTask());
                viewsMap.get(DescribeSwingTaskName).show();
            } catch (Exception e) {
                view.showError(e.getMessage());
            }
        }

        //Открываем окно календаря задач
        if (event.getActionCommand().equals(TaskView.ACTION_SHOW_CALENDAR)) {

            viewsMap.get(CalendarSwingTaskName).show();
        }

        if (event.getActionCommand().equals(TaskView.ACTION_ADD)) {
            try {
                //Обновляем модель
                model.addTask(view.getTask());
                view.close();
            }
            catch (Exception e) {
                view.showError(e.getMessage());
            }
        }

        if (event.getActionCommand().equals(TaskView.ACTION_EDIT)) {
            try {
                //Обновляем модель
                model.changeTask(model.getSelTask(), view.getTask());
                view.close();
            }
            catch (Exception e) {
                view.showError(e.getMessage());
            }
        }

        if (event.getActionCommand().equals(TaskView.ACTION_DESC)) {
            try {
                //Обновляем модель
                model.changeTask(model.getSelTask(), view.getTask());
                model.setSelTask(view.getTask());
            }
            catch (Exception e) {
                view.showError(e.getMessage());
            }
        }

        if (event.getActionCommand().equals(TaskView.ACTION_DEL)) {
            try {
                //Обновляем модель
                model.removeTask(view.getTask());
            }
            catch (Exception e) {
                view.showError(e.getMessage());
            }
        }
    }

    public void createView(TaskViewFactory factory) {
        TaskView view = factory.createView(model);
        viewsMap.put(view.getClass().getName(), view);
        views.add(view);
        view.addActionListener(this);
    }

    TaskModel model;
    ArrayList views = new ArrayList();
    SortedMap<String, TaskView> viewsMap = new TreeMap<>();
}
