package ua.sumdu.j2se.bokoch.lab1.view.timepicker;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bokoch on 28.02.2017.
 */
public class TimePicker extends JPopupMenu {
    private JPanel panel;
    private JSpinner spinnerHour, spinnerMin, spinnerSec;

    public TimePicker() {
        panel = new JPanel();
        GridLayout gridLayout = new GridLayout(2,3,12,0);
        panel.setLayout(gridLayout);

        spinnerHour =  new JSpinner(new SpinnerNumberModel(0,0,23,1));
        spinnerHour.setEditor(new JSpinner.NumberEditor(spinnerHour, "00"));
        spinnerHour.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                getTime();
            }
        });
        panel.add(spinnerHour);

        spinnerMin =  new JSpinner(new SpinnerNumberModel(0,0,59,1));
        spinnerMin.setEditor(new JSpinner.NumberEditor(spinnerMin, "00"));
        panel.add(spinnerMin);

        spinnerSec =  new JSpinner(new SpinnerNumberModel(0,0,59,1));
        spinnerSec.setEditor(new JSpinner.NumberEditor(spinnerSec, "00"));
        panel.add(spinnerSec);

        JLabel labHour = new JLabel();
        labHour.setText("Hours");
        panel.add(labHour);

        JLabel labMin = new JLabel();
        labMin.setText("Minutes");
        panel.add(labMin);

        JLabel labSec = new JLabel();
        labSec.setText("Seconds");
        panel.add(labSec);

        add(panel);
        setSize(new Dimension(176,45));
    }

    public String getTime() {
        NumberFormat formatter = new DecimalFormat("00");
        return (formatter.format(spinnerHour.getValue()) + ":" + formatter.format(spinnerMin.getValue()) + ":" +
                formatter.format(spinnerSec.getValue()));
    }

    public void setTime(Date time) {
        spinnerHour.setValue(Integer.parseInt(new SimpleDateFormat("HH").format(time)));
        spinnerMin.setValue(Integer.parseInt(new SimpleDateFormat("mm").format(time)));
        spinnerSec.setValue(Integer.parseInt(new SimpleDateFormat("ss").format(time)));
    }

    public void clear() {
        spinnerHour.setValue(8);
        spinnerMin.setValue(0);
        spinnerSec.setValue(0);
    }
}
