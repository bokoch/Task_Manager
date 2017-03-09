package ua.sumdu.j2se.bokoch.lab1.view;

import ua.sumdu.j2se.bokoch.lab1.model.TaskModel;
import ua.sumdu.j2se.bokoch.tasks.Task;
import ua.sumdu.j2se.bokoch.tasks.TaskList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Реализация главного вида, со списком всех задач
 */
public class MainSwingTaskView extends SwingTaskView implements Observer {
    final String FRAME_TITLE = "Main";
    final String ADD_BUTTON_TITLE = "Add";
    final String DEL_BUTTON_TITLE = "Remove";
    final String EDIT_BUTTON_TITLE = "Edit";
    final String CALEND_BUTTON_TITLE = "Calendar";
    final String DESC_BUTTON_TITLE = "Describe";
    final String CLOSE_BUTTON_TITLE = "Close";

    public MainSwingTaskView(TaskModel model) {
        super(model);
        model.observable().addObserver(this);
        show();
        update(null, null);
    }

    /**
     * Создаем фабрику для производства таких видов
     */
    public static TaskViewFactory getFactory() {
        return new TaskViewFactory() {
            @Override
            public TaskView createView(TaskModel model) {
                return new MainSwingTaskView(model);
            }
        };
    }

    @Override
    public void createFrame() {
        frame = new JFrame(FRAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                fireAction(ACTION_CLOSE);
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(null);

        list = new JList(listModel);
        list.setFocusable(false);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(0,0,540,200);
        panel.add(scrollPane);

        JButton addButton = new JButton(ADD_BUTTON_TITLE);
        addButton.setBounds(10,210,70,30);
        panel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_SHOW_ADD);
            }
        });

        JButton editButton = new JButton(EDIT_BUTTON_TITLE);
        editButton.setBounds(90,210,70,30);
        panel.add(editButton);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_SHOW_EDIT);
            }
        });

        JButton descButton = new JButton(DESC_BUTTON_TITLE);
        descButton.setBounds(170,210,85,30);
        panel.add(descButton);
        descButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_SHOW_DESC);
            }
        });

        JButton delButton = new JButton(DEL_BUTTON_TITLE);
        delButton.setBounds(265,210,85,30);
        panel.add(delButton);
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_DEL);
            }
        });


        JButton calendarButton = new JButton(CALEND_BUTTON_TITLE);
        calendarButton.setBounds(360,210,85,30);
        panel.add(calendarButton);
        calendarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_SHOW_CALENDAR);
            }
        });

        JButton closeButton = new JButton(CLOSE_BUTTON_TITLE);
        closeButton.setBounds(455,210,85,30);
        panel.add(closeButton);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_CLOSE);
            }
        });

        frame.setPreferredSize((new Dimension(560,300)));
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);

    }

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
     * Возвращает выбраную из списка задачу
     * Если задача в списке не выбрана выбрасывает исключение
     */
    @Override
    public Task getTask() throws ParseException {
        if(list.isSelectionEmpty())
            throw new RuntimeException("Choose task!");
        Task tmpTask = parseStrTask((String) list.getSelectedValue());
        return tmpTask;
    }

    @Override
    public void update(Observable source, Object arg) {
        listModel.clear();
        strTasks = showStringList(model.getList());
        for (int i = 0; i < strTasks.size(); i++) {
            listModel.addElement(strTasks.get(i));
        }
    }

    private Task parseStrTask(String tmpStr) throws ParseException {
        String title, activeStr, dateStr, tmp, startStr, endStr, timeStr, checkStr, repeat;
        int dayI, hrI, minI, secI, interval;
        Date start, end, time;
        boolean active;
        Task tmpTask;
        title = ((tmp = tmpStr.substring(0, tmpStr.lastIndexOf('"'))).substring(tmp.indexOf('"') + 1));

        activeStr = ((tmp = tmpStr.substring(0, tmpStr.lastIndexOf("") - 1)).substring(tmp.lastIndexOf(']') + 1));
        if (activeStr.equals(" inactive"))
            active = false;
        else
            active = true;

        checkStr = ((tmp = tmpStr.substring(0, tmpStr.indexOf('['))).substring(tmp.lastIndexOf('"') + 1));
        if (checkStr.equals(" from ")) {
            dateStr = ((tmp = tmpStr.substring(0, tmpStr.lastIndexOf("["))).substring(tmp.indexOf("[")));
            startStr = ((tmp = dateStr.substring(0, dateStr.indexOf("]"))).substring(tmp.indexOf("[") + 1));
            endStr = ((tmp = dateStr.substring(0, dateStr.lastIndexOf("]"))).substring(tmp.lastIndexOf("[") + 1));
            start = (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(startStr).clone();
            end = (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(endStr).clone();
            repeat = ((tmp = tmpStr.substring(0, tmpStr.lastIndexOf("]"))).substring(tmp.lastIndexOf("[") + 1));

            Matcher mDay = Pattern.compile("\\d+.day").matcher(repeat);
            dayI = parseInterval(repeat, mDay);
            Matcher mHr = Pattern.compile("\\d+.hour").matcher(repeat);
            hrI = parseInterval(repeat, mHr);
            Matcher mMin = Pattern.compile("\\d+.minute").matcher(repeat);
            minI = parseInterval(repeat, mMin);
            Matcher mSec = Pattern.compile("\\d+.second").matcher(repeat);
            secI = parseInterval(repeat, mSec);
            interval = dayI * 86400 + hrI * 3600 + minI * 60 + secI;
            tmpTask = new Task(title, start, end, interval);

        } else {
            timeStr = ((tmp = tmpStr.substring(0, tmpStr.indexOf("]"))).substring(tmp.indexOf("[") + 1));
            time = (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(timeStr).clone();
            tmpTask = new Task(title, time);
        }
        tmpTask.setActive(active);


        return tmpTask;
    }

    private static int parseInterval(String str, Matcher m) {
        int res = 0;
        while (m.find()) {
            String subStr;
            subStr = str.substring(m.start(), m.end());
            Matcher match = Pattern.compile("\\d+").matcher(subStr);
            while (match.find())
                res = Integer.parseInt(subStr.substring(match.start(), match.end()));
        }
        return res;
    }

    /**
     * Представляет массив задач, как массив строк с описанием задач
     */
    private ArrayList<String> showStringList(TaskList tasks) {
        ArrayList<String> strTasks = new ArrayList<>();

        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.getTask(i);
            String str = "";
            if (!task.isRepeated()) {
                str += "\"" + task.getTitle() + "\" " + "at " + "[" + smp.format(task.getTime().getTime()) + "]";
            } else {
                str += "\"" + task.getTitle() + "\" " + "from " + "[" + smp.format(task.getStartTime().getTime()) + "] ";
                str += "to" + " [" + smp.format(task.getEndTime().getTime()) + "] every ";
                String formatInterval = "";
                int interval = task.getRepeatInterval();
                int days = interval / 86400;
                int hours = (interval % 86400) / 3600;
                int minutes = ((interval % 86400) % 3600) / 60;
                int sec = (((interval % 86400) % 3600) % 60);
                if (days != 0) {
                    formatInterval += " " + String.valueOf(days) + " day";
                    if (days > 1)
                        formatInterval += "s";
                }
                if (hours != 0) {
                    formatInterval += " " + String.valueOf(hours) + " hour";
                    if (hours > 1)
                        formatInterval += "s";
                }
                if (minutes != 0) {
                    formatInterval += " " + String.valueOf(minutes) + " minute";
                    if (minutes > 1)
                        formatInterval += "s";
                }
                if (sec != 0) {
                    formatInterval += " " + String.valueOf(sec) + " second";
                    if (sec > 1)
                        formatInterval += "s";
                }
                str += "[" + formatInterval.substring(1) + "]";
            }
            if (!tasks.getTask(i).isActive())
                str += " inactive;";
            else
                str += ";";
            strTasks.add(str);
        }
        return strTasks;
    }

    private ArrayList<String> strTasks;
    private final DefaultListModel listModel = new DefaultListModel();
    private JList list;
}
