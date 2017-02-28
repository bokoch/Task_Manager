package ua.sumdu.j2se.bokoch.lab1.view;

import ua.sumdu.j2se.bokoch.lab1.model.TaskModel;
import ua.sumdu.j2se.bokoch.tasks.Task;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Реализация вида для простмотра задачи
 */
public class DescribeSwingTask extends SwingTaskView {
    private String BUTTON_CLOSE_TITLE = "Close";
    private String FRAME_TITLE = "Describe task";

    public DescribeSwingTask(TaskModel model) {
        super(model);
    }

    public static TaskViewFactory getFactory() {
        return new TaskViewFactory() {
            @Override
            public TaskView createView(TaskModel model) {
                return new DescribeSwingTask(model);
            }
        };
    }

    /**
     * Создает окно
     */
    @Override
    public void createFrame() {
        frame = new JFrame(FRAME_TITLE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        Font labelFont = new Font("Arial", Font.PLAIN, 14);

        JLabel titleLabel = new JLabel("Title");
        titleLabel.setFont(labelFont);
        titleLabel.setBounds(30,20,80,25);
        panel.add(titleLabel);

        titleTask = new JTextField();
        titleTask.setAutoscrolls(true);
        titleTask.setEditable(false);
        titleTask.setBackground(Color.white);
        titleTask.setBounds(70,20,280,25);
        panel.add(titleTask);

        titleStart = new JLabel("Start");
        titleStart.setFont(labelFont);
        titleStart.setBounds(25,50,80,25);
        panel.add(titleStart);

        startTime =  new JTextField();
        startTime.setEditable(false);
        startTime.setBackground(Color.white);
        startTime.setBounds(70,50,120,25);
        panel.add(startTime);

        titleEnd = new JLabel("End");
        titleEnd.setFont(labelFont) ;
        titleEnd.setBounds(200,50,80,25);
        panel.add(titleEnd);

        endTime = new JTextField();
        endTime.setEditable(false);
        endTime.setBackground(Color.white);
        endTime.setBounds(230,50,120,25);
        panel.add(endTime);

        titleInterval = new JLabel("Interval");
        titleInterval.setFont(labelFont) ;
        titleInterval.setBounds(10,80,80,25);
        panel.add(titleInterval);

        intervalTask = new JTextField();
        intervalTask.setBackground(Color.white);
        intervalTask.setEditable(false);
        intervalTask.setBounds(70,80,280,25);
        panel.add(intervalTask);

        titleNext = new JLabel("Next time");
        titleNext.setFont(labelFont) ;
        titleNext.setBounds(5,110,80,25);
        panel.add(titleNext);

        nextTime = new JTextField();
        nextTime.setEditable(false);
        nextTime.setBackground(Color.white);
        nextTime.setBounds(70,110,280,25);
        panel.add(nextTime);

        chkActive = new JCheckBox("Active task");
        chkActive.setFont(labelFont) ;
        chkActive.setBounds(5,140,120,25);
        panel.add(chkActive);
        chkActive.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                fireAction(ACTION_DESC);
            }
        });

        closeBtn = new JButton(BUTTON_CLOSE_TITLE);
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_CLOSE);
            }
        });
        closeBtn.setBounds(140,170,85,30);
        panel.add(closeBtn);

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setPreferredSize((new Dimension(380,260)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
    }

    /**
     * Заполнить текстовые поля при открытии окна
     */
    private void fillFrame() {
        titleTask.setText(model.getSelTask().getTitle());
        SimpleDateFormat smp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        if(model.getSelTask().isRepeated()) {
            repeatFlag = true;
            startTime.setText(smp.format(model.getSelTask().getStartTime()));
            endTime.setText(smp.format(model.getSelTask().getEndTime()));
            intervalTask.setText(formatInterval(model.getSelTask()));

            titleNext.setBounds(5,110,80,25);
            startTime.setBounds(70,50,120,25);
            nextTime.setBounds(70,110,280,25);
            chkActive.setBounds(5,140,120,25);
            closeBtn.setBounds(140,170,85,30);
            frame.setSize(380,260);

            titleStart.setText("Start");
            titleEnd.setVisible(true);
            endTime.setVisible(true);
            titleInterval.setVisible(true);
            intervalTask.setVisible(true);
        } else {
            repeatFlag = false;
            startTime.setText(smp.format(model.getSelTask().getTime()));

            startTime.setBounds(70,50,280,25);
            titleNext.setBounds(5,80,80,25);
            nextTime.setBounds(70,80,280,25);
            chkActive.setBounds(5,110,150,25);
            closeBtn.setBounds(140,140,85,30);
            frame.setSize(380,230);

            titleStart.setText("Time");
            titleEnd.setVisible(false);
            endTime.setVisible(false);
            titleInterval.setVisible(false);
            intervalTask.setVisible(false);
        }
        Date nextTimeAfter = model.getSelTask().nextTimeAfter(new Date());
        if(nextTimeAfter == null)
            nextTime.setText("Never");
        else
            nextTime.setText(smp.format(nextTimeAfter));
        chkActive.setSelected(model.getSelTask().isActive());
    }

    /**
     * Форматировать вывод интервала, для текстового поля
     * @param task
     * @return formatInterval
     */
    private String formatInterval(Task task) {
        String formatInterval = "";
        int interval = model.getSelTask().getRepeatInterval();
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
        return formatInterval;
    }

    /**
     * Открыть окно
     */
    @Override
    public void show() {
        fillFrame();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
            }
        });
    }

    /**
     * Возвращает обьект типа Task
     * @return tmpTask
     * @throws ParseException
     */
    @Override
    public Task getTask() throws ParseException {
        Task tmpTask;

        Date start;
        Date end;
        int interval;
        SimpleDateFormat smp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        if(!repeatFlag) {
            start = smp.parse(startTime.getText());
            tmpTask = new Task(titleTask.getText(), start);
        }
        else {
            start = smp.parse(startTime.getText());;
            end = smp.parse(endTime.getText());
            String repeat = intervalTask.getText();

            Matcher mDay = Pattern.compile("\\d+.day").matcher(repeat);
            int dayI = parseInterval(repeat, mDay);
            Matcher mHr = Pattern.compile("\\d+.hour").matcher(repeat);
            int hrI = parseInterval(repeat, mHr);
            Matcher mMin = Pattern.compile("\\d+.minute").matcher(repeat);
            int minI = parseInterval(repeat, mMin);
            Matcher mSec = Pattern.compile("\\d+.second").matcher(repeat);
            int secI = parseInterval(repeat, mSec);
            interval = dayI * 86400 + hrI * 3600 + minI * 60 + secI;

            tmpTask = new Task(titleTask.getText(), (Date) start.clone(), (Date) end.clone(), interval);
        }
        tmpTask.setActive(chkActive.isSelected());
        return tmpTask;
    }

    /**
     * Возвращет численное значение интервала из строки
     * @param str
     * @param m
     * @return res
     */
    private int parseInterval(String str, Matcher m) {
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

    private JCheckBox chkActive;
    private boolean repeatFlag;
    private JLabel titleStart, titleEnd, titleInterval, titleNext;
    private JButton closeBtn;
    private JTextField titleTask, startTime, endTime, intervalTask, nextTime;
}
