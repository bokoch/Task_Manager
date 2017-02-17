package ua.sumdu.j2se.bokoch.lab1.view;

import ua.sumdu.j2se.bokoch.lab1.model.TaskModel;
import ua.sumdu.j2se.bokoch.tasks.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Реализация вида, для добавления задач
 */
public class AddSwingTaskView extends SwingTaskView {
    private String BUTTON_ADD_TITLE = "Add";
    private String BUTTON_CANCEL_TITLE = "Cancel";
    private String FRAME_TITLE = "Add task";

    public AddSwingTaskView(TaskModel model) {
        super(model);
    }

    public static TaskViewFactory getFactory() {
        return new TaskViewFactory() {
            @Override
            public TaskView createView(TaskModel model) {
                return new AddSwingTaskView(model);
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

        JLabel titleLabel = new JLabel("Title",JLabel.LEFT);
        titleLabel.setFont(labelFont);
        titleLabel.setBounds(20,20,80,25);
        panel.add(titleLabel);

        titleTask = new JTextField();
        titleTask.setAutoscrolls(true);
        titleTask.setBounds(60,20,280,25);
        panel.add(titleTask);

        JLabel titleStart = new JLabel("Start");
        titleStart.setFont(labelFont);
        titleStart.setBounds(15,50,80,25);
        panel.add(titleStart);

        startTime =  new JSpinner(new SpinnerDateModel());
        startTime.setEditor(new JSpinner.DateEditor(startTime, "dd.MM.yyyy HH:mm"));
        startTime.setBounds(60,50,120,25);
        panel.add(startTime);

        JLabel titleEnd = new JLabel("End");
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
        rbRep.setSelected(true);
        rbNoRep.setFont(labelFont);
        rbRep.setFont(labelFont);
        buttonGroup.add(rbNoRep);
        buttonGroup.add(rbRep);
        rbRep.setBounds(350,20,150,25);
        rbNoRep.setBounds(350,50,150,25);
        panel.add(rbNoRep);
        panel.add(rbRep);

        JLabel titleInterval = new JLabel("Interval");
        titleInterval.setFont(labelFont) ;
        titleInterval.setBounds(5,80,80,25);
        panel.add(titleInterval);

        JLabel titleDay = new JLabel("Days");
        titleDay.setBounds(70,100,55,25);
        panel.add(titleDay);
        JLabel titleHr = new JLabel("Hours");
        titleHr.setBounds(140,100,55,25);
        panel.add(titleHr);
        JLabel titleMin = new JLabel("Min");
        titleMin.setBounds(225,100,55,25);
        panel.add(titleMin);
        JLabel titleSec = new JLabel("Sec");
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
                endTime.setVisible(true);
                titleEnd.setVisible(true);
                spinDay.setVisible(true);
                spinHr.setVisible(true);
                spinMin.setVisible(true);
                spinSec.setVisible(true);
                titleDay.setVisible(true);
                titleHr.setVisible(true);
                titleMin.setVisible(true);
                titleSec.setVisible(true);
                titleInterval.setVisible(true);
            }
        });
        rbNoRep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                titleStart.setText("Time");
                startTime.setSize(280,25);
                endTime.setVisible(false);
                titleEnd.setVisible(false);
                spinDay.setVisible(false);
                spinHr.setVisible(false);
                spinMin.setVisible(false);
                spinSec.setVisible(false);
                titleDay.setVisible(false);
                titleHr.setVisible(false);
                titleMin.setVisible(false);
                titleSec.setVisible(false);
                titleInterval.setVisible(false);

            }
        });

        JButton addBtn = new JButton(BUTTON_ADD_TITLE);
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_ADD);
            }
        });
        addBtn.setBounds(140,130,85,30);
        panel.add(addBtn);

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
     * Возвращает введенную пользователем задачу
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
     * Задать окно "Add task" видимым
     */
    @Override
    public void show() {
        clear();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
            }
        });
    }

    /**
     * Очистить значения полей
     */
    public void clear() {
        titleTask.setText("");
        startTime.setValue(new Date());
        endTime.setValue(new Date());
        chkActive.setSelected(false);
        spinDay.setValue(0);
        spinHr.setValue(0);
        spinMin.setValue(0);
        spinSec.setValue(0);
    }

    private JCheckBox chkActive;
    private JRadioButton rbNoRep, rbRep;
    private JSpinner spinDay, spinHr, spinMin, spinSec;
    private JSpinner endTime, startTime;
    private JTextField titleTask;
}
