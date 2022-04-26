package edu.hitsz;

import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPage{
    private JButton easyButton;
    private JButton normalButton;
    private JButton hardButton;
    private JPanel modePanel;
    private JPanel topPanel;
    private JPanel musicPanel;
    private JComboBox comboBox1;
    private JLabel 音效;


    public MenuPage() {
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (Main.object) {
                    Main.MODE = 0;
                    Main.object.notify();
                }
            }
        });
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (Main.object) {
                    Main.MODE = 1;
                    Main.object.notify();
                }
            }
        });
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                synchronized (Main.object) {
                    Main.MODE = 2;
                    Main.object.notify();
                }
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text=comboBox1.getSelectedItem().toString();
                if(text=="开"){
                    Main.MUSIC_FLAG=true;
                }
                else{
                    Main.MUSIC_FLAG=false;
                }
            }
        });

    }
    public JPanel getMenuPanel() {
        return topPanel;
    }

}
