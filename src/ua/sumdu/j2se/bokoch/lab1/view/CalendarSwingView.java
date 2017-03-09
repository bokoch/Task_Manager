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

    /**
     * Создаем фабрику для производства таких видов
     */
    public static TaskViewFactory getFactory() {
        return new TaskViewFactory() {
            @Override
            public TaskView createView(TaskModel model) {
                return new CalendarSwingView(model);
            }
        };
    }

    @Override
    public void createFrame() {
        frame = new JFrame(FRAME_TITLE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        Font labelFont = new Font("Arial", Font.PLAIN, 14);

        JLabel titleStart = new JLabel("Start");
        titleStart.setFont(labelFont);
        titleStart.setBounds(10,130,50,25);
        panel.add(titleStart);

        startDate =  new JDateChooser(new Date());
        startDate.setBounds(50,130,120,25);
        panel.add(startDate);

        startTimePicker = new TimePicker();
        startTime = new JTextField();
        startTime.setText(startTimePicker.getTime());
        startTime.setBounds(180, 130, 80,25);
        panel.add(startTime);

        timeStartPickerBtn = new JButton("...");
        timeStartPickerBtn.setBounds(260,130,20,25);
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
            public void mouseClicked(final MouseEvent e) {
                if((e.getModifiers() & e.BUTTON1_MASK) != 0) {
                    startTimePicker.setVisible(false);
                    startTime.setText(startTimePicker.getTime());

                }
            }
        });

        JLabel titleEnd = new JLabel("End");
        titleEnd.setFont(labelFont) ;
        titleEnd.setBounds(10,170,50,25);
        panel.add(titleEnd);

        endDate = new JDateChooser(new Date(new Date().getTime() + 24*60*60));
        panel.add(endDate);
        endDate.setBounds(50,170,120,25);

        endTimePicker = new TimePicker();
        endTime = new JTextField();
        endTime.setText(endTimePicker.getTime());
        endTime.setBounds(180, 170, 80,25);
        panel.add(endTime);

        timeEndPickerBtn = new JButton("...");
        timeEndPickerBtn.setBounds(260,170,20,25);
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

        list = new JList(listModel);
        list.setFocusable(false);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(0,0,305,120);
        panel.add(scrollPane);

        JButton showBtn = new JButton(BUTTON_SHOW_TITLE);
        SimpleDateFormat smp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        //По нажатию кнопки выводить рассписание задач по заданой дате
        showBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date dateFrom = null;
                Date dateTo = null;
                try {
                    dateFrom = smp.parse(((JTextField) startDate.getDateEditor()).getText() + " " + startTime.getText());
                    dateTo = smp.parse(((JTextField) endDate.getDateEditor()).getText() + " " + endTime.getText());
                } catch (ParseException ex) {
                    viewLogger.error(ex.getMessage());
                }
                setList(model.getCalendarMap(dateFrom, dateTo));
            }
        });
        showBtn.setBounds(60,210,85,30);
        panel.add(showBtn);

        JButton closeBtn = new JButton(BUTTON_CLOSE_TITLE);
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_CLOSE);
            }
        });
        closeBtn.setBounds(160,210,85,30);
        panel.add(closeBtn);

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setPreferredSize((new Dimension(320,300)));
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void show() {
        listModel.clear();
        startTimePicker.clear();
        endTimePicker.clear();
        startTime.setText(startTimePicker.getTime());
        endTime.setText(endTimePicker.getTime());
        startDate.setDate(new Date());
        endDate.setDate(new Date(new Date().getTime() + 24*60*60));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
            }
        });
    }

    /**
     * Заполнить список календарем задач
     */
    public void setList(SortedMap<Date, Set<Task>> calendar) {
        listModel.clear();
        SimpleDateFormat smp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        if(calendar.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There is no tasks for this period of time!",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            viewLogger.info("Для заданного промежутка времени задач нету");
        }
        for (SortedMap.Entry<Date, Set<Task>> item: calendar.entrySet()) {
            listModel.addElement(smp.format(item.getKey()) + ": " + getTasks(item.getValue()));
        }
    }

    /**
     * Возвращает строку задач, что заданы в Set
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
    private JDateChooser endDate, startDate;
    private JTextField startTime, endTime;
    private TimePicker startTimePicker, endTimePicker;
    private JButton timeStartPickerBtn, timeEndPickerBtn;
}
