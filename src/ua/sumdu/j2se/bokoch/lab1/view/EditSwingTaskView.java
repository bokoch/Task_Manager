package ua.sumdu.j2se.bokoch.lab1.view;

import com.toedter.calendar.JDateChooser;
import ua.sumdu.j2se.bokoch.lab1.model.TaskModel;
import ua.sumdu.j2se.bokoch.lab1.view.timepicker.TimePicker;
import ua.sumdu.j2se.bokoch.tasks.Task;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Реализация вида для редактирования задачи
 */
public class EditSwingTaskView extends SwingTaskView {
    private String BUTTON_EDIT_TITLE = "Edit";
    private String BUTTON_CANCEL_TITLE = "Cancel";
    private String FRAME_TITLE = "Edit Task";

    public EditSwingTaskView(TaskModel model) {
        super(model);
    }

    public static TaskViewFactory getFactory() {
        return new TaskViewFactory() {
            @Override
            public TaskView createView(TaskModel model) {
                return new EditSwingTaskView(model);
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

        JLabel titleLabel = new JLabel("Title",JLabel.LEFT);
        titleLabel.setFont(labelFont);
        titleLabel.setBounds(20,20,80,25);
        panel.add(titleLabel);

        titleTask = new JTextField();
        titleTask.setAutoscrolls(true);
        titleTask.setBounds(60,20,230,25);
        panel.add(titleTask);

        titleStart = new JLabel("Start");
        titleStart.setFont(labelFont);
        titleStart.setBounds(15,50,80,25);
        panel.add(titleStart);

        startDate = new JDateChooser(new Date());
        startDate.setBounds(60,50,120,25);
        panel.add(startDate);

        startTimePicker = new TimePicker();
        startTime = new JTextField();
        startTime.setText(startTimePicker.getTime());
        startTime.setBounds(190, 50, 80,25);
        panel.add(startTime);

        timeStartPickerBtn = new JButton("...");
        timeStartPickerBtn.setBounds(270,50,20,25);
        panel.add(timeStartPickerBtn);

        timeStartPickerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTime.setText(startTimePicker.getTime());
                int x = timeStartPickerBtn.getX() + timeStartPickerBtn.getBounds().width - startTimePicker.getBounds().width + 8;
                int y = timeStartPickerBtn.getY() + timeStartPickerBtn.getBounds().height + startTimePicker.getBounds().height - 12;
                startTimePicker.show(frame, x, y);
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(final MouseEvent e) {
                if((e.getModifiers() & e.BUTTON1_MASK) != 0) {
                    startTimePicker.setVisible(false);
                    startTime.setText(startTimePicker.getTime());

                }
            }
        });

        titleEnd = new JLabel("End");
        titleEnd.setFont(labelFont) ;
        titleEnd.setBounds(15,80,80,25);
        panel.add(titleEnd);

        endDate = new JDateChooser(new Date());
        panel.add(endDate);
        endDate.setBounds(60,80,120,25);

        endTimePicker = new TimePicker();

        endTime = new JTextField();
        endTime.setText(endTimePicker.getTime());
        endTime.setBounds(190, 80, 80,25);
        panel.add(endTime);

        timeEndPickerBtn = new JButton("...");
        timeEndPickerBtn.setBounds(270,80,20,25);
        panel.add(timeEndPickerBtn);
        timeEndPickerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endTime.setText(endTimePicker.getTime());
                int x = timeEndPickerBtn.getX() + timeEndPickerBtn.getBounds().width - endTimePicker.getBounds().width + 8;
                int y = timeEndPickerBtn.getY() + timeEndPickerBtn.getBounds().height + endTimePicker.getBounds().height - 12;
                endTimePicker.show(frame, x, y);
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(final MouseEvent e) {
                if((e.getModifiers() & e.BUTTON1_MASK) != 0) {
                    endTimePicker.setVisible(false);
                    endTime.setText(endTimePicker.getTime());

                }
            }
        });

        chkActive = new JCheckBox("Active task");
        chkActive.setFont(labelFont) ;
        chkActive.setBounds(300,80,120,25);
        panel.add(chkActive);

        ButtonGroup buttonGroup = new ButtonGroup();
        rbNoRep = new JRadioButton("No Repeated");
        rbRep = new JRadioButton("Repeated");
        rbRep.setSelected(true);
        rbNoRep.setFont(labelFont);
        rbRep.setFont(labelFont);
        buttonGroup.add(rbNoRep);
        buttonGroup.add(rbRep);
        rbRep.setBounds(300,20,150,25);
        rbNoRep.setBounds(300,50,150,25);
        panel.add(rbNoRep);
        panel.add(rbRep);

        titleInterval = new JLabel("Interval");
        titleInterval.setFont(labelFont) ;
        titleInterval.setBounds(5,110,80,25);
        panel.add(titleInterval);

        titleDay = new JLabel("Days");
        titleDay.setBounds(65,130,55,25);
        panel.add(titleDay);
        titleHr = new JLabel("Hours");
        titleHr.setBounds(125,130,55,25);
        panel.add(titleHr);
        titleMin = new JLabel("Min");
        titleMin.setBounds(195,130,55,25);
        panel.add(titleMin);
        titleSec = new JLabel("Sec");
        titleSec.setBounds(255,130,55,25);
        panel.add(titleSec);

        spinDay = new JSpinner();
        JSpinner.DefaultEditor editorDay = (JSpinner.DefaultEditor) spinDay.getEditor();
        editorDay.getTextField().setEditable(false);
        spinDay.setBounds(60,110,45,25);

        spinHr = new JSpinner(new SpinnerNumberModel(0,0,23,1));
        JSpinner.DefaultEditor editorHr = (JSpinner.DefaultEditor) spinHr.getEditor();
        editorHr.getTextField().setEditable(false);
        spinHr.setBounds(120,110,45,25);

        spinMin = new JSpinner(new SpinnerNumberModel(0,0,59,1));
        JSpinner.DefaultEditor editorMin = (JSpinner.DefaultEditor) spinMin.getEditor();
        editorMin.getTextField().setEditable(false);
        spinMin.setBounds(185,110,45,25);

        spinSec = new JSpinner(new SpinnerNumberModel(0,0,59,1));
        JSpinner.DefaultEditor editorSec = (JSpinner.DefaultEditor) spinSec.getEditor();
        editorSec.getTextField().setEditable(false);
        spinSec.setBounds(245,110,45,25);
        panel.add(spinDay);
        panel.add(spinHr);
        panel.add(spinMin);
        panel.add(spinSec);

        editBtn = new JButton(BUTTON_EDIT_TITLE);
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_EDIT);
            }
        });
        editBtn.setBounds(110,170,85,30);
        panel.add(editBtn);

        cancelBtn = new JButton(BUTTON_CANCEL_TITLE);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_CLOSE);
            }
        });
        cancelBtn.setBounds(210,170,85,30);
        panel.add(cancelBtn);

        rbRep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                titleStart.setText("Start");
                frame.setSize((new Dimension(430,260)));
                editBtn.setBounds(100,170,85,30);
                cancelBtn.setBounds(200,170,85,30);
                setVisible(true);
            }
        });
        rbNoRep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                titleStart.setText("Time");
                frame.setSize((new Dimension(430,180)));
                editBtn.setBounds(100,100,85,30);
                cancelBtn.setBounds(200,100,85,30);
                setVisible(false);
            }
        });

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setPreferredSize((new Dimension(430,260)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
    }

    /**
     * Задать параметр setVisible для компонентов, которые используются для различных типов задач
     * @param flag
     */
    public void setVisible(boolean flag) {
        endDate.setVisible(flag);
        endTime.setVisible(flag);
        timeEndPickerBtn.setVisible(flag);
        titleEnd.setVisible(flag);
        spinDay.setVisible(flag);
        spinHr.setVisible(flag);
        spinMin.setVisible(flag);
        spinSec.setVisible(flag);
        titleDay.setVisible(flag);
        titleHr.setVisible(flag);
        titleMin.setVisible(flag);
        titleSec.setVisible(flag);
        titleInterval.setVisible(flag);
    }

    /**
     * Возвращает задачу введенную пользователем
     * @return
     */
    @Override
    public Task getTask() {

        Task tmpTask = null;
        Date start = null;
        Date end = null;
        int interval;
        SimpleDateFormat smp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        if(titleTask.getText().isEmpty())
            throw new RuntimeException("Title must be not null!");

        if(rbNoRep.isSelected()) {
            try {
                start = smp.parse(((JTextField) startDate.getDateEditor()).getText() + " " + startTime.getText());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tmpTask = new Task(titleTask.getText(), start);
        }
        else if (rbRep.isSelected()) {
            try {
                start = smp.parse(((JTextField) startDate.getDateEditor()).getText() + " " + startTime.getText());
                end = smp.parse(((JTextField) endDate.getDateEditor()).getText() + " " + endTime.getText());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            interval = (int) spinDay.getValue()*86400 + (int) spinHr.getValue()*3600 + (int) spinMin.getValue()*60 +
                    (int) spinSec.getValue();
            tmpTask = new Task(titleTask.getText(), (Date) start.clone(), (Date) end.clone(), interval);
        }
        tmpTask.setActive(chkActive.isSelected());
        return tmpTask;
    }

    /**
     * Открывает окно
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
     * Заполняет окно информацией о задаче при открытии
     */
    private void fillFrame() {
        titleTask.setText(model.getSelTask().getTitle());
        SimpleDateFormat smp = new SimpleDateFormat("HH:mm:ss");
        startTime.setText(smp.format(model.getSelTask().getStartTime()));
        startDate.setDate(model.getSelTask().getStartTime());
        startTimePicker.setTime(model.getSelTask().getStartTime());
        chkActive.setSelected(model.getSelTask().isActive());
        if(model.getSelTask().isRepeated()) {
            rbRep.setSelected(true);
            titleStart.setText("Start");
            setVisible(true);
            endTime.setText(smp.format(model.getSelTask().getEndTime()));
            endDate.setDate(model.getSelTask().getEndTime());
            endTimePicker.setTime(model.getSelTask().getEndTime());

            frame.setSize((new Dimension(430,260)));
            editBtn.setBounds(100,170,85,30);
            cancelBtn.setBounds(200,170,85,30);

            int interval = model.getSelTask().getRepeatInterval();
            spinDay.setValue(interval / 86400);
            spinHr.setValue((interval % 86400) / 3600);
            spinMin.setValue(((interval % 86400) % 3600) / 60);
            spinSec.setValue(((interval % 86400) % 3600) % 60);
        } else {
            rbNoRep.setSelected(true);
            titleStart.setText("Time");

            frame.setSize((new Dimension(430,180)));
            editBtn.setBounds(100,100,85,30);
            cancelBtn.setBounds(200,100,85,30);

            setVisible(false);
            spinDay.setValue(0);
            spinHr.setValue(0);
            spinMin.setValue(0);
            spinSec.setValue(0);

        }
    }

    private JCheckBox chkActive;
    private JRadioButton rbNoRep, rbRep;
    private JSpinner spinDay, spinHr, spinMin, spinSec;
    private JDateChooser endDate, startDate;
    private TimePicker startTimePicker, endTimePicker;
    private JButton timeEndPickerBtn, timeStartPickerBtn, editBtn, cancelBtn;
    private JLabel titleStart, titleEnd, titleInterval, titleDay, titleHr, titleMin, titleSec;
    private JTextField titleTask, startTime, endTime;
}
