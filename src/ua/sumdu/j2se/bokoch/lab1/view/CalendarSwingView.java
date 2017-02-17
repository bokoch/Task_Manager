package ua.sumdu.j2se.bokoch.lab1.view;

import ua.sumdu.j2se.bokoch.lab1.model.TaskModel;
import ua.sumdu.j2se.bokoch.tasks.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;

/**
 * Реализация вида, вывода календаря задач
 */
public class CalendarSwingView extends SwingTaskView {

    private String BUTTON_SHOW_TITLE = "Show";
    private String BUTTON_CLOSE_TITLE = "Close";
    private String FRAME_TITLE = "Calendar";

    public CalendarSwingView(TaskModel model) {
        super(model);
    }

    public static TaskViewFactory getFactory() {
        return new TaskViewFactory() {
            @Override
            public TaskView createView(TaskModel model) {
                return new CalendarSwingView(model);
            }
        };
    }

    /**
     * Создать окно
     */
    @Override
    public void createFrame() {
        frame = new JFrame(FRAME_TITLE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        Font labelFont = new Font("Arial", Font.PLAIN, 14);

        JLabel titleStart = new JLabel("Start");
        titleStart.setFont(labelFont);
        titleStart.setBounds(60,130,80,25);
        panel.add(titleStart);

        startTime =  new JSpinner(new SpinnerDateModel());
        startTime.setEditor(new JSpinner.DateEditor(startTime, "dd.MM.yyyy HH:mm"));
        startTime.setBounds(100,130,120,25);
        panel.add(startTime);

        JLabel titleEnd = new JLabel("End");
        titleEnd.setFont(labelFont) ;
        titleEnd.setBounds(240,130,80,25);
        panel.add(titleEnd);

        endTime = new JSpinner(new SpinnerDateModel());
        endTime.setEditor(new JSpinner.DateEditor(endTime, "dd.MM.yyyy HH:mm"));
        panel.add(endTime);
        endTime.setBounds(270,130,120,25);

        list = new JList(listModel);
        list.setFocusable(false);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(0,0,480,120);
        panel.add(scrollPane);

        JButton showBtn = new JButton(BUTTON_SHOW_TITLE);
        //По нажатию кнопки выводить рассписание задач по заданой дате
        showBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setList(model.getCalendarMap((Date) startTime.getValue(), (Date) endTime.getValue()));
            }
        });
        showBtn.setBounds(140,170,85,30);
        panel.add(showBtn);

        JButton closeBtn = new JButton(BUTTON_CLOSE_TITLE);
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_CLOSE);
            }
        });
        closeBtn.setBounds(240,170,85,30);
        panel.add(closeBtn);

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setPreferredSize((new Dimension(480,250)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);

    }

    /**
     * Заполнить список
     * @param calendar
     */
    public void setList(SortedMap<Date, Set<Task>> calendar) {
        listModel.clear();
        SimpleDateFormat smp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        for (SortedMap.Entry<Date, Set<Task>> item: calendar.entrySet()) {
            listModel.addElement(smp.format(item.getKey()) + ": " + getTasks(item.getValue()));
        }
    }

    /**
     * Возвращает строку задач, что заданы в Set
     * @param tasks
     * @return str
     */
    public String getTasks(Set<Task> tasks) {
        String str = "";
        Iterator<Task> iter = tasks.iterator();
        while (iter.hasNext()) {
            str += iter.next().getTitle();
            if(iter.hasNext())
                str += ", ";
            else
                str += ".";
        }
        return str;
    }

    @Override
    public Task getTask() {
        return null;
    }

    private final DefaultListModel listModel = new DefaultListModel();
    private JList list;
    private JSpinner endTime, startTime;
}
