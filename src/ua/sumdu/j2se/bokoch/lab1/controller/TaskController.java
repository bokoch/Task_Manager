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
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.*;

/**
 * Контроллер.
 * Обрабатывает события и обновляет модель
 */
public class TaskController implements ActionListener {
    String AddSwingTaskName = AddSwingTaskView.class.getName();
    String CalendarSwingTaskName = CalendarSwingView.class.getName();
    String DescribeSwingTaskName = DescribeSwingTask.class.getName();
    String EditSwingTaskName = EditSwingTaskView.class.getName();
    String MainSwingTaskName = MainSwingTaskView.class.getName();
    private static final Logger controllerLogger = LogManager.getLogger(TaskController.class);

    /**
     * Создаем контроллер для модели и виды для нее
     */
    public static void main(String[] args) {
        controllerLogger.info("Запуск программы");
        TaskList arr = new ArrayTaskList();
        try {
            TaskIO.readText(arr, new File("d:\\text1.txt"));
            controllerLogger.info("Задачи успешно считаны с файла");
        } catch (IOException e) {
            controllerLogger.fatal(e.getMessage());
        } catch (ParseException e) {
            controllerLogger.fatal(e.getMessage());
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

    /**
     * Обрабатываем события от вида
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        TaskView view = (TaskView) event.getSource();
        if (event.getActionCommand().equals(view.ACTION_CLOSE)) {
            view.close();
            if(event.getSource().equals(viewsMap.get(MainSwingTaskName))) {
                try {
                    TaskIO.writeText(model.getList(), new File("d:\\text1.txt"));
                    controllerLogger.info("Задачи успешно сохранены в файл");
                } catch (IOException e) {
                    controllerLogger.fatal(e.getMessage());
                }
                controllerLogger.info("Программа завершена");
                System.exit(0);
            }
        }
        //Открываем окно добавления задачи
        if (event.getActionCommand().equals(TaskView.ACTION_SHOW_ADD)) {
            viewsMap.get(AddSwingTaskName).show();
            controllerLogger.info("Открыть окно добавления задачи");
        }
        //Сохраняем выбранную задачу и передаем в окно редактирования задачи
        //Открываем окно редактирования задачи
        if (event.getActionCommand().equals(TaskView.ACTION_SHOW_EDIT)) {
            try {
                model.setSelTask(view.getTask());
                viewsMap.get(EditSwingTaskName).show();
                controllerLogger.info("Открыть окно редактирования задачи");
            } catch (Exception e) {
                view.showError(e.getMessage());
                controllerLogger.warn(e.getMessage());
            }
        }

        //Сохраняем выбранную задачу и передаем в окно описания задачи
        //Открываем окно описания задачи
        if (event.getActionCommand().equals(TaskView.ACTION_SHOW_DESC)) {
            try {
                model.setSelTask(view.getTask());
                viewsMap.get(DescribeSwingTaskName).show();
                controllerLogger.info("Открыть окно описания задачи");
            } catch (Exception e) {
                view.showError(e.getMessage());
                controllerLogger.warn(e.getMessage());
            }
        }

        //Открываем окно календаря задач
        if (event.getActionCommand().equals(TaskView.ACTION_SHOW_CALENDAR)) {
            viewsMap.get(CalendarSwingTaskName).show();
            controllerLogger.info("Открыть окно календаря задач");
        }

        if (event.getActionCommand().equals(TaskView.ACTION_ADD)) {
            try {
                //Обновляем модель
                model.addTask(view.getTask());
                controllerLogger.info("Добавлена задача");
                view.close();
            }
            catch (Exception e) {
                view.showError(e.getMessage());
                controllerLogger.error(e.getMessage());
            }
        }

        if (event.getActionCommand().equals(TaskView.ACTION_EDIT)) {
            try {
                //Обновляем модель
                model.changeTask(model.getSelTask(), view.getTask());
                controllerLogger.info("Задача изменена");
                view.close();
            }
            catch (Exception e) {
                view.showError(e.getMessage());
                controllerLogger.error(e.getMessage());
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
                controllerLogger.error(e.getMessage());
            }
        }

        if (event.getActionCommand().equals(TaskView.ACTION_DEL)) {
            try {
                //Обновляем модель
                model.removeTask(view.getTask());
                controllerLogger.info("Задача удалена");
            }
            catch (Exception e) {
                view.showError(e.getMessage());
                controllerLogger.warn(e.getMessage());
            }
        }
    }

    /**
     * Сохдаем вид используя фабрику
     */
    public void createView(TaskViewFactory factory) {
        TaskView view = factory.createView(model);
        viewsMap.put(view.getClass().getName(), view);
        view.addActionListener(this);
    }

    TaskModel model;
    SortedMap<String, TaskView> viewsMap = new TreeMap<>();
}
