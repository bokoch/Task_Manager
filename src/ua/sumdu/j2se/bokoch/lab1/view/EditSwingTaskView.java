package ua.sumdu.j2se.bokoch.lab1.view;

import ua.sumdu.j2se.bokoch.lab1.model.TaskModel;
import ua.sumdu.j2se.bokoch.tasks.Task;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        titleTask.setBounds(60,20,280,25);
        panel.add(titleTask);

        titleStart = new JLabel("Start");
        titleStart.setFont(labelFont);
        titleStart.setBounds(15,50,80,25);
        panel.add(titleStart);

        startTime =  new JSpinner(new SpinnerDateModel());
        startTime.setEditor(new JSpinner.DateEditor(startTime, "dd.MM.yyyy HH:mm"));
        startTime.setBounds(60,50,120,25);
        panel.add(startTime);

        titleEnd = new JLabel("End");
        titleEnd.setFont(labelFont) ;
        titleEnd.setBounds(190,50,80,25);
        panel.add(titleEnd);

        endTime = new JSpinner(new SpinnerDateModel());
        endTime.setEditor(new JSpinner.DateEditor(endTime, "dd.MM.yyyy HH:mm"));
        panel.add(endTime);
        endTime.setBounds(220,50,120,25);

        chkActive = new JCheckBox("Active task");
        chkActive.setFont(labelFont) ;
        chkActive.setBounds(350,80,120,25);
        panel.add(chkActive);

        ButtonGroup buttonGroup = new ButtonGroup();
        rbNoRep = new JRadioButton("No Repeated");
        rbRep = new JRadioButton("Repeated");
        rbNoRep.setFont(labelFont);
        rbRep.setFont(labelFont);
        buttonGroup.add(rbNoRep);
        buttonGroup.add(rbRep);
        rbRep.setBounds(350,20,150,25);
        rbNoRep.setBounds(350,50,150,25);
        panel.add(rbNoRep);
        panel.add(rbRep);

        titleInterval = new JLabel("Interval");
        titleInterval.setFont(labelFont) ;
        titleInterval.setBounds(5,80,80,25);
        panel.add(titleInterval);

        titleDay = new JLabel("Days");
        titleDay.setBounds(70,100,55,25);
        panel.add(titleDay);
        titleHr = new JLabel("Hours");
        titleHr.setBounds(140,100,55,25);
        panel.add(titleHr);
        titleMin = new JLabel("Min");
        titleMin.setBounds(225,100,55,25);
        panel.add(titleMin);
        titleSec = new JLabel("Sec");
        titleSec.setBounds(295,100,55,25);
        panel.add(titleSec);

        spinDay = new JSpinner();
        JSpinner.DefaultEditor editorDay = (JSpinner.DefaultEditor) spinDay.getEditor();
        editorDay.getTextField().setEditable(false);
        spinDay.setBounds(60,80,55,25);

        spinHr = new JSpinner(new SpinnerNumberModel(0,0,23,1));
        JSpinner.DefaultEditor editorHr = (JSpinner.DefaultEditor) spinHr.getEditor();
        editorHr.getTextField().setEditable(false);
        spinHr.setBounds(135,80,55,25);

        spinMin = new JSpinner(new SpinnerNumberModel(0,0,59,1));
        JSpinner.DefaultEditor editorMin = (JSpinner.DefaultEditor) spinMin.getEditor();
        editorMin.getTextField().setEditable(false);
        spinMin.setBounds(210,80,55,25);

        spinSec = new JSpinner(new SpinnerNumberModel(0,0,59,1));
        JSpinner.DefaultEditor editorSec = (JSpinner.DefaultEditor) spinSec.getEditor();
        editorSec.getTextField().setEditable(false);
        spinSec.setBounds(285,80,55,25);
        panel.add(spinDay);
        panel.add(spinHr);
        panel.add(spinMin);
        panel.add(spinSec);

        rbRep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                titleStart.setText("Start");
                startTime.setSize(120,25);
                setVisible(true);
            }
        });
        rbNoRep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                titleStart.setText("Time");
                startTime.setSize(280,25);
                setVisible(false);
            }
        });

        JButton editBtn = new JButton(BUTTON_EDIT_TITLE);
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_EDIT);
            }
        });
        editBtn.setBounds(140,130,85,30);
        panel.add(editBtn);

        JButton cancelBtn = new JButton(BUTTON_CANCEL_TITLE);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_CLOSE);
            }
        });
        cancelBtn.setBounds(240,130,85,30);
        panel.add(cancelBtn);

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setPreferredSize((new Dimension(480,220)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
    }

    /**
     * Задать параметр setVisible для компонентов, которые используются для различных типов задач
     * @param flag
     */
    public void setVisible(boolean flag) {
        endTime.setVisible(flag);
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

        Date start;
        Date end;
        int interval;

        if(titleTask.getText().isEmpty())
            throw new RuntimeException("Title must be not null!");

        if(rbNoRep.isSelected()) {
            start = (Date) startTime.getValue();
            tmpTask = new Task(titleTask.getText(), start);
        }
        else if (rbRep.isSelected()) {
            start = (Date) startTime.getValue();
            end = (Date) endTime.getValue();
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
        SimpleDateFormat smp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        startTime.setValue(model.getSelTask().getStartTime());
        chkActive.setSelected(model.getSelTask().isActive());
        if(model.getSelTask().isRepeated()) {
            rbRep.setSelected(true);
            titleStart.setText("Start");
            startTime.setSize(120,25);
            setVisible(true);
            endTime.setValue(model.getSelTask().getEndTime());
            int interval = model.getSelTask().getRepeatInterval();
            spinDay.setValue(interval / 86400);
            spinHr.setValue((interval % 86400) / 3600);
            spinMin.setValue(((interval % 86400) % 3600) / 60);
            spinSec.setValue(((interval % 86400) % 3600) % 60);
        } else {
            rbNoRep.setSelected(true);
            titleStart.setText("Time");
            startTime.setSize(280,25);
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
    private JLabel titleStart, titleEnd, titleInterval, titleDay, titleHr, titleMin, titleSec;
    private JSpinner endTime, startTime;
    private JTextField titleTask;
}
