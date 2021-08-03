package si.projektna.unit29.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;


public class BottomPanel extends JPanel implements MouseListener {

    JLabel label;
    JComboBox<String> box;
    JTextField text;
    JButton button;
    JButton toggle;
    JButton refresh;
    JButton exit;

    private SimpleDateFormat formatter = new SimpleDateFormat("EEE dd/MM/yyyy");

    private String FIRST = " 1  >  " + formatter.format(Date.from(Instant.now().minus(Duration.ofDays(1)))) + "  ";
    private String SECOND = " 2  >  " + formatter.format(Date.from(Instant.now().minus(Duration.ofDays(2)))) + "  ";
    private String THIRD = " 3  >  " + formatter.format(Date.from(Instant.now().minus(Duration.ofDays(3)))) + "  ";
    private String FOURTH = " 4  >  " + formatter.format(Date.from(Instant.now().minus(Duration.ofDays(4)))) + "  ";
    private String FIFTH = " 5  >  " + formatter.format(Date.from(Instant.now().minus(Duration.ofDays(5)))) + "  ";

    private String[] daysAgo = { FIRST, SECOND, THIRD, FOURTH, FIFTH };


    BottomPanel() {

        label = new JLabel(" days ago ");
        label.setForeground(Color.RED);

        box = new JComboBox<>(daysAgo);
        box.setSize(100, 40);
        box.setBackground(Color.GRAY.brighter());
        box.setForeground(Color.MAGENTA.darker());

        text = new JTextField("select city", 20);
        text.setSize(300, 50);
        // text.setMargin(new Insets(0, 50, 0, 50));
        text.addMouseListener(this);


        button = new JButton("OK");
        button.setBackground(Color.LIGHT_GRAY);
        button.setSize(100, 50);
        button.setEnabled(false);

        toggle = new JButton("Toggle");
        toggle.setBackground(Color.LIGHT_GRAY);
        toggle.setSize(100, 50);
        toggle.setVisible(false);


        refresh = new JButton("Reset");
        refresh.setBackground(Color.LIGHT_GRAY);
        refresh.setSize(100, 50);

        exit = new JButton("Exit");
        exit.setBackground(Color.LIGHT_GRAY);
        exit.setSize(100, 50);

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(label);
        this.add(box);
        this.add(text);
        this.add(button);
        this.add(toggle);
        this.add(refresh);
        this.add(exit, BorderLayout.EAST);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == text) {
            text.setText("");
            button.setEnabled(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }

    public String getFIRST() {
        return FIRST;
    }

    public String getSECOND() {
        return SECOND;
    }

    public String getTHIRD() {
        return THIRD;
    }

    public String getFOURTH() {
        return FOURTH;
    }

    public String getFIFTH() {
        return FIFTH;
    }
}

