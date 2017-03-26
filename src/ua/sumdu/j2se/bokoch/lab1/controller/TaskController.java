package ua.sumdu.j2se.bokoch.lab1.controller;

import ua.sumdu.j2se.bokoch.lab1.model.DefaultTaskModel;
import ua.sumdu.j2se.bokoch.lab1.model.TaskModel;
import ua.sumdu.j2se.bokoch.lab1.view.*;
import ua.sumdu.j2se.bokoch.tasks.ArrayTaskList;
import ua.sumdu.j2se.bokoch.tasks.Task;
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

    public static String filePath = "d:\\text1.txt";
    /**
     * Создаем контроллер для модели и виды для нее
     */
    public static void main(String[] args) {
        controllerLogger.info("Start program");
        TaskList arr = new ArrayTaskList();
        try {
            TaskIO.readText(arr, new File(filePath));
            controllerLogger.info("Tasks are successfully read from file: \"" + filePath + "\"");
        } catch (IOException e) {
            controllerLogger.error("IOException: ", e);
        } catch (ParseException e) {
            controllerLogger.error("Parse Exception: ", e);
        } catch (RuntimeException e) {
            controllerLogger.error("Task parse exception",e);
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
                    TaskIO.writeText(model.getList(), new File(filePath));
                    controllerLogger.info("Tasks are successfully saved to file: \"" + filePath + "\"");
                } catch (IOException e) {
                    controllerLogger.error("IOException: ", e);
                }
                controllerLogger.info("Program closed");
                System.exit(0);
            }
        }
        //Открываем окно добавления задачи
        if (event.getActionCommand().equals(TaskView.ACTION_SHOW_ADD)) {
            viewsMap.get(AddSwingTaskName).show();
            controllerLogger.info("Open \"Add Task\" window");
        }
        //Сохраняем выбранную задачу и передаем в окно редактирования задачи
        //Открываем окно редактирования задачи
        if (event.getActionCommand().equals(TaskView.ACTION_SHOW_EDIT)) {
            try {
                model.setSelTask(view.getTask());
                viewsMap.get(EditSwingTaskName).show();
                controllerLogger.info("Open \"Edit Task\" window");
            } catch (Exception e) {
                view.showError(e.getMessage());
                controllerLogger.warn("Choose task", e);
            }
        }

        //Сохраняем выбранную задачу и передаем в окно описания задачи
        //Открываем окно описания задачи
        if (event.getActionCommand().equals(TaskView.ACTION_SHOW_DESC)) {
            try {
                model.setSelTask(view.getTask());
                viewsMap.get(DescribeSwingTaskName).show();
                controllerLogger.info("Open \"Describe Task\" window");
            } catch (Exception e) {
                view.showError(e.getMessage());
                controllerLogger.warn("Choose task", e);
            }
        }

        //Открываем окно календаря задач
        if (event.getActionCommand().equals(TaskView.ACTION_SHOW_CALENDAR)) {
            viewsMap.get(CalendarSwingTaskName).show();
            controllerLogger.info("Open \"Calendar of tasks\" window");
        }

        if (event.getActionCommand().equals(TaskView.ACTION_ADD)) {
            try {
                //Обновляем модель
                model.addTask(view.getTask());
                controllerLogger.info("[" + view.getTask() + "] is added");
                view.close();
            }
            catch (Exception e) {
                view.showError(e.getMessage());
                controllerLogger.error("Bad task", e);
            }
        }

        if (event.getActionCommand().equals(TaskView.ACTION_EDIT)) {
            try {
                //Обновляем модель
                model.changeTask(model.getSelTask(), view.getTask());
                controllerLogger.info("Task is edited: \r\n\t[" + model.getSelTask() +
                        "] changed to \r\n\t[" + view.getTask() + "]");
                view.close();
            }
            catch (Exception e) {
                view.showError(e.getMessage());
                controllerLogger.error("Bad task", e);
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
                controllerLogger.error("Bad task", e);
            }
        }

        if (event.getActionCommand().equals(TaskView.ACTION_DEL)) {
            try {
                //Обновляем модель
                Task tmpTask;
                model.removeTask(tmpTask = view.getTask());
                controllerLogger.info("\"" + tmpTask + "\" is deleted");
            }
            catch (Exception e) {
                view.showError(e.getMessage());
                controllerLogger.warn("Choose task", e);
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
